package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ExecutionGraph {
    private static final Map<Long, ExecutionGraph> createdExecutions = new HashMap<>();

    @Autowired
    ObjectProvider<NodeExecutionWrapper> nodeExecutionWrapperObjectProvider;

    private final Long executionId;
    private final Collection<GraphNode> startNodes;
    private final Map<Long, NodeExecutionWrapper> nodeExecutions;

    public ExecutionGraph(Long executionId, Collection<GraphNode> startNodes) {
        this.executionId = executionId;
        this.startNodes = startNodes;
        nodeExecutions = new HashMap<>();
        createdExecutions.put(executionId, this);
    }

    public static ExecutionGraph getByExecutionId(Long executionId) {
        return createdExecutions.get(executionId);
    }

    public CompletableFuture<Void> run() {
        return buildExecutionFuture();
    }

    public void forceCompleteNode(Long nodeId) {
        nodeExecutions.get(nodeId).forceCompleteTask();
    }

    private CompletableFuture<Void> buildExecutionFuture() {
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
                NodeExecutionWrapper executionWrapper = nodeExecutionWrapperObjectProvider.getObject(this, currentNode);
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

        return CompletableFuture.allOf(leafFutures.toArray(new CompletableFuture[] {}));
    }

    public Long getExecutionId() {
        return executionId;
    }
}
