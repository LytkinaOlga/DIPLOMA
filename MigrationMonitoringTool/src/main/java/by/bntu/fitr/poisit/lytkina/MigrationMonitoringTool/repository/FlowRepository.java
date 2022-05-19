package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.JPAEdgeRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.JPAFlowRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.JPANodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FlowRepository {
    @Autowired
    JPAFlowRepository flowRepository;
    @Autowired
    JPANodeRepository nodeRepository;
    @Autowired
    JPAEdgeRepository edgeRepository;


    public Collection<Flow> findAll() {
        Collection<FlowJPA> flowJPAs = flowRepository.findAll();
        return flowJPAs.stream()
            .map(this::loadFlowNodesAndEdges)
            .collect(Collectors.toList());
    }

    public Optional<Flow> findById(Long flowId) {
        Optional<FlowJPA> flowJPA = flowRepository.findById(flowId);
        return flowJPA.map(this::loadFlowNodesAndEdges);
    }

    @Transactional
    public Flow recreate(Flow flow) {
        removeFlowElements(flow);

        FlowJPA flowJPA = new FlowJPA(flow);
        flowJPA = flowRepository.save(flowJPA);
        final Long flowId = flowJPA.getId();

        Collection<NodeJPA> oldNodes = flow.getNodes();
        Map<Long, NodeJPA> newNodes = oldNodes.stream()
            .peek(n -> n.setFlow(flowId))
            .collect(Collectors.toMap(
                NodeJPA::getId,
                nodeRepository::save
            ));

        List<EdgeJPA> edges = flow.getEdges().stream()
            .map(oldEdge -> {
                NodeJPA newFrom = newNodes.get(oldEdge.getNodeFrom().getId());
                NodeJPA newTo = newNodes.get(oldEdge.getNodeTo().getId());

                EdgeJPA newEdge = new EdgeJPA();
                newEdge.setNodeFrom(newFrom);
                newEdge.setNodeTo(newTo);

                return edgeRepository.save(newEdge);
            })
            .collect(Collectors.toList());

        flow.setId(flowJPA.getId());
        flow.setNodes(newNodes.values());
        flow.setEdges(edges);

        return flow;
    }

    private Flow loadFlowNodesAndEdges(FlowJPA flowJPA) {
        Flow flow = new Flow(flowJPA);
        Collection<NodeJPA> nodes = nodeRepository.findAllByFlowId(flow.getId());
        Collection<EdgeJPA> edges = new ArrayList<>();
        nodes.forEach(node -> edges.addAll(edgeRepository.findAllByNodeFromId(node.getId())));

        flow.setNodes(nodes);
        flow.setEdges(edges);
        return flow;
    }

    public void removeFlowElements(Flow flow) {
        if (flow.getId() == null) {
            return;
        }
        Collection<NodeJPA> removedNodes = nodeRepository.findAllByFlowId(flow.getId());
        removedNodes.forEach(
            node -> edgeRepository.deleteAllByNodeFromId(node.getId())
        );
        nodeRepository.deleteAll(removedNodes);
    }
}
