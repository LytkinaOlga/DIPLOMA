package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.*;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.EdgeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.FlowJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.NodeJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FlowRepository {
    @Autowired
    NodeJPARepository nodeRepository;

    public void removeFlowElements(Flow flow) {
        if (flow.getId() == null) {
            return;
        }
        Collection<NodeJPA> removedNodes = nodeRepository.findAllByFlowId(flow.getId());
        removedNodes.forEach(
            node -> edgeRepository.deleteAllByNodeFrom(node.getId())
        );
        nodeRepository.deleteAll(removedNodes);
    }


    @Autowired
    FlowJPARepository flowRepository;

    @Autowired
    EdgeJPARepository edgeRepository;


    public Collection<Flow> findAll() {
        Collection<FlowJPA> flowJPAs = flowRepository.findAll();
//        return null;
        return flowJPAs.stream()
            .map(Flow::new)
            .collect(Collectors.toList());
    }

    public Optional<Flow> findById(Long flowId) {
        Optional<FlowJPA> flowJPA = flowRepository.findById(flowId);
        return flowJPA.map(this::loadFlowNodesAndEdges);
    }

    public void instantiateExecution(Flow flow, Long executionId) {
        // copy nodes
        // copy edegs
    }

    /**
     * Recreates nodes and edges.
     */
    @Transactional
    public Flow updateFlow(Flow flow) {
        removeFlowElements(flow);

        FlowJPA flowJPA = shallowSave(flow);
        final Long flowId = flowJPA.getId();

        Collection<NodeJPA> oldNodes = flow.getNodes();
        Map<Long, NodeJPA> newNodes = oldNodes.stream()
            .collect(Collectors.toMap(
                NodeJPA::getId,
                oldNode -> {
                    NodeJPA newNode = NodeJPA.copyWithoutFlowAndEdgesAndId(oldNode);
                    newNode.setFlow(flowJPA);
                    return nodeRepository.save(newNode);
                }
            ));

        List<EdgeJPA> edges = flow.getEdges().stream()
            .map(oldEdge -> {
                NodeJPA newFrom = newNodes.get(oldEdge.getNodeFrom());
                NodeJPA newTo = newNodes.get(oldEdge.getNodeTo());

                EdgeJPA newEdge = new EdgeJPA();
                newEdge.setNodeFrom(newFrom.getId());
                newEdge.setNodeTo(newTo.getId());

                return edgeRepository.save(newEdge);
            })
            .collect(Collectors.toList());

        flow.setId(flowId);
        flow.setNodes(newNodes.values());
        flow.setEdges(edges);

        return flow;
    }

    private Flow loadFlowNodesAndEdges(FlowJPA flowJPA) {
        Collection<NodeJPA> nodes = nodeRepository.findAllDeepByFlowId(flowJPA.getId());

        Collection<EdgeJPA> edges = new HashSet<>();
        nodes.forEach(node -> {
            edges.addAll(node.getOutgoingEdges());
            edges.addAll(node.getIncomingEdges());
        });

        Flow flow = new Flow(flowJPA);
        flow.setNodes(nodes);
        flow.setEdges(edges);
        return flow;
    }

    private Flow loadFlowNodesAndEdgesOld(FlowJPA flowJPA) {
        Flow flow = new Flow(flowJPA);
        Collection<NodeJPA> nodes = nodeRepository.findAllByFlowId(flow.getId());
        Collection<EdgeJPA> edges = new ArrayList<>();
        nodes.forEach(node -> edges.addAll(edgeRepository.findAllByNodeFrom(node.getId())));

        flow.setNodes(nodes);
        flow.setEdges(edges);
        return flow;
    }

    private FlowJPA shallowSave(Flow flow) {
        Optional<FlowJPA> flowJPAOptional = flowRepository.findById(flow.getId());
        FlowJPA flowJPA;
        if (flowJPAOptional.isPresent()) {
            flowJPA = flowJPAOptional.get();
            flowJPA.shallowMerge(flow);
            flowJPA.setModificationDate(new Date());
        } else {
            flowJPA = FlowJPA.shallowCopy(flow);
        }
        return flowRepository.save(flowJPA);
    }
}
