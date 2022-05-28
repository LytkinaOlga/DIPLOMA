package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Execution;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionNodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionProgressJPAPK;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ExecutionRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.TransactionalParamUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ExecutionGraph {
    private static final Map<Long, ExecutionGraph> createdExecutions = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ExecutionGraph.class);

    @Autowired
    ObjectProvider<NodeExecutionWrapper> nodeExecutionWrapperObjectProvider;

    @Autowired
    ExecutionJPARepository executionJPARepository;

    @Autowired
    ExecutionRepository executionRepository;

    @Autowired
    TransactionalParamUpdater paramUpdater;

    private final Long flowId;
    private final Collection<GraphNode> startNodes;
    private final Map<Long, NodeExecutionWrapper> nodeExecutions;
    private CompletableFuture<Void> executionFuture;

    public ExecutionGraph(Long flowId, Collection<GraphNode> startNodes) {
        this.flowId = flowId;
        this.startNodes = startNodes;
        nodeExecutions = new HashMap<>();
    }

    public static ExecutionGraph getByExecutionId(Long executionId) {
        return createdExecutions.get(executionId);
    }

    public Execution run() {
        Execution execution = executionRepository.instantiateByFlow(flowId);
        createdExecutions.put(execution.getId(), this);
        executionFuture = buildExecutionFuture(execution.getId());
        paramUpdater.updateExecution(execution.getId(), ExecutionStatus.RUNNING, new Date(), null);

        return execution;
    }

    public void forceCompleteNode(Long nodeId) {
        nodeExecutions.get(nodeId).forceCompleteTask();
    }

    private CompletableFuture<Void> buildExecutionFuture(Long executionId) {
        Map<GraphNode, CompletableFuture<Void>> nodeFutures = new HashMap<>();
        Collection<CompletableFuture<Void>> leafFutures = new ArrayList<>();

        Collection<GraphNode> nextStep = new LinkedList<>(startNodes);
        Collection<GraphNode> currentStep;

        outer: while (!nextStep.isEmpty()) {
            currentStep = nextStep;
            nextStep = new ArrayList<>();

            for (GraphNode currentNode : currentStep) {
                if (nodeFutures.containsKey(currentNode)) {
                    continue;
                }
                Collection<CompletableFuture<Void>> nodeDependencies = new ArrayList<>();
                for (GraphNode incomingNode : currentNode.getIncomingNodes()) {
                    if (nodeFutures.containsKey(incomingNode)) {
                        nodeDependencies.add(nodeFutures.get(incomingNode));
                    } else {
                        // can't build node future until all incoming nodes build theirs
                        break outer;
                    }
                }
                NodeExecutionWrapper executionWrapper = nodeExecutionWrapperObjectProvider.getObject(
                    this, currentNode, executionId
                );
                CompletableFuture<Void> nodeFuture = nodeDependencies.isEmpty() ?
                    CompletableFuture.runAsync(executionWrapper)
                    :
                    CompletableFuture.allOf(
                        nodeDependencies.toArray(new CompletableFuture[] {})
                    ).thenRun(executionWrapper);

                nodeFutures.put(currentNode, nodeFuture);
                nodeExecutions.put(currentNode.getNodeId(), executionWrapper);
                if (currentNode.getOutgoingNodes() == null || currentNode.getOutgoingNodes().isEmpty()) {
                    leafFutures.add(nodeFuture);
                } else {
                    nextStep.addAll(currentNode.getOutgoingNodes());
                }
            }
        }

        CompletableFuture<Void> graphFuture = CompletableFuture.allOf(
            leafFutures.toArray(new CompletableFuture[] {})
        );

        graphFuture.thenRun(() -> {
            paramUpdater.updateExecution(executionId, ExecutionStatus.SUCCEEDED, null, new Date());
        });

        graphFuture.exceptionally(e -> {
            logger.error("Execution failed, flowId: {}, executionId: {}", flowId, executionId, e);
            paramUpdater.updateExecution(executionId, ExecutionStatus.FAILED, null, new Date(), e.getMessage());
            return null;
        });

        return graphFuture;
    }

    public Long getFlowId() {
        return flowId;
    }
}
