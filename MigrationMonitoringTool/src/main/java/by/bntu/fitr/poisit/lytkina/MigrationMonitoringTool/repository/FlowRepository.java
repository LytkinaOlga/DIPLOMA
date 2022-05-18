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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
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

    private Flow loadFlowNodesAndEdges(FlowJPA flowJPA) {
        Flow flow = new Flow(flowJPA);
        Collection<NodeJPA> nodes = nodeRepository.findAllByFlowId(flow.getId());
        Collection<EdgeJPA> edges = new ArrayList<>();
        nodes.forEach(node -> edges.addAll(edgeRepository.findAllByNodeFromId(node.getId())));

        flow.setNodes(nodes);
        flow.setEdges(edges);
        return flow;
    }
}
