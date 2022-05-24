package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionJPARepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ExecutionGraph {
    private static final Map<Long, ExecutionGraph> createdExecutions = new HashMap<>();

    @Autowired
    ObjectProvider<NodeExecutionWrapper> nodeExecutionWrapperObjectProvider;

    @Autowired
    ExecutionJPARepository executionJPARepository;

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

    public ExecutionJPA run() {
        ExecutionJPA executionJPA = new ExecutionJPA();
        executionJPA.setStatus(ExecutionStatus.RUNNING);
        executionJPA.setStartDate(new Date());
        executionJPA.setFlowJPA(new FlowJPA(flowId));
        executionJPARepository.save(executionJPA);

        executionFuture = buildExecutionFuture(executionJPA.getId());

        createdExecutions.put(executionJPA.getId(), this);

        return executionJPA;
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
            ExecutionJPA executionJPA = executionJPARepository.findById(executionId).get();
            executionJPA.setStatus(ExecutionStatus.SUCCEEDED);
            executionJPARepository.save(executionJPA);
        });

        return graphFuture;
    }

    public Long getFlowId() {
        return flowId;
    }
}
