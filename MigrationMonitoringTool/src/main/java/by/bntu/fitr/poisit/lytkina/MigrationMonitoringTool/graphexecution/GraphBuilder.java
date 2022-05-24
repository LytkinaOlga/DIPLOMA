package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.EdgeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.NodeJPARepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Component
public class GraphBuilder {
    @Autowired
    NodeJPARepository nodeRepository;
    @Autowired
    EdgeJPARepository edgeRepository;
    @Autowired
    ObjectProvider<ExecutionGraph> executionGraphObjectProvider;

    public ExecutionGraph buildGraph(Long executionId) {
        Collection<NodeJPA> nodes = nodeRepository.findAllDeepByFlowId(executionId);
        Collection<GraphNode> startNodes = new ArrayList<>();
        Map<Long, GraphNode> graphNodeMap = new HashMap<>();

        for (NodeJPA nodeJPA : nodes) {
            graphNodeMap.put(nodeJPA.getId(), new GraphNode());
        }
        for (NodeJPA nodeJPA : nodes) {
            GraphNode graphNode = graphNodeMap.get(nodeJPA.getId());
            graphNode.setNodeId(nodeJPA.getId());

            graphNode.setIncomingNodes(
                mapCollect(nodeJPA.getIncomingEdges(), e -> graphNodeMap.get(e.getNodeFrom()))
            );
            graphNode.setOutgoingNodes(
                mapCollect(nodeJPA.getOutgoingEdges(), e -> graphNodeMap.get(e.getNodeTo()))
            );

            if (graphNode.getIncomingNodes().isEmpty()) {
                startNodes.add(graphNode);
            }
        }

        return executionGraphObjectProvider.getObject(executionId, startNodes);
    }

    public ExecutionGraph fakeBuildGraph() {
        Set<GraphNode> startNodes = new HashSet<>();

        GraphNode A = new GraphNode("A", Collections.emptyList());
        GraphNode B = new GraphNode("B", Collections.emptyList());
        GraphNode C = new GraphNode("C", Collections.singleton(A));
        GraphNode D = new GraphNode("D", Arrays.asList(A, B));
        GraphNode E = new GraphNode("E", Arrays.asList(C, D));
        GraphNode F = new GraphNode("F", Collections.singleton(C));
        GraphNode G = new GraphNode("G", Collections.singleton(B));
        GraphNode I = new GraphNode("I", Collections.singleton(E));

        A.setOutgoingNodes(Arrays.asList(C, D));
        B.setOutgoingNodes(Arrays.asList(D, G));
        C.setOutgoingNodes(Arrays.asList(F, E));
        D.setOutgoingNodes(Arrays.asList(E));
        E.setOutgoingNodes(Arrays.asList(I));


        return new ExecutionGraph(null, Arrays.asList(A, B));
    }
}
