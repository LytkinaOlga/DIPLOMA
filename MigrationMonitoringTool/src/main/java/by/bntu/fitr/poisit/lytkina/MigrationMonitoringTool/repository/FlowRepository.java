package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FlowDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.NodeDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.*;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Component
public class FlowRepository {
    @Autowired
    NodeJPARepository nodeRepository;

    @Autowired
    TaskJPARepository taskRepository;

    @Autowired
    TaskParameterJPARepository taskParameterRepository;

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
    public Long updateFlow(FlowDTO flowDTO) {
        removeFlowElements(flowDTO);

        FlowJPA flowJPA = shallowSave(flowDTO);

        Collection<NodeDTO> nodeDTOs = flowDTO.getNodes();
        Map<Long, NodeJPA> newNodes = nodeDTOs.stream()
            .collect(Collectors.toMap(
                NodeDTO::getId,
                nodeDTO -> {
                    NodeJPA newNode = NodeJPA.shallowCopy(nodeDTO);
                    newNode.setTask(taskRepository.getById(Long.valueOf(nodeDTO.getTaskId())));

                    newNode.setParameters(mapCollect(nodeDTO.getNodeParametersDTO(), p -> {
                        TaskParameterJPA taskParam = taskParameterRepository.getById(Long.valueOf(p.getParamId()));
                        NodeParameterJPA nodeParam = new NodeParameterJPA();
                        nodeParam.setParameter(taskParam);
                        nodeParam.setValue(p.getValue());
                        nodeParam.setNode(newNode);
                        return nodeParam;
                    }));
                    newNode.setFlow(flowJPA);
                    return newNode;
                }
            ));
        nodeRepository.saveAll(newNodes.values());

        List<EdgeJPA> edges = flowDTO.getEdges().stream()
            .map(edgeDTO -> {
                NodeJPA newFrom = newNodes.get(Long.valueOf(edgeDTO.getSource()));
                NodeJPA newTo = newNodes.get(Long.valueOf(edgeDTO.getTarget()));

                EdgeJPA newEdge = new EdgeJPA();
                newEdge.setNodeFrom(newFrom.getId());
                newEdge.setNodeTo(newTo.getId());

                return newEdge;
            })
            .collect(Collectors.toList());
        edgeRepository.saveAll(edges);

        return flowJPA.getId();
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

    private FlowJPA shallowSave(FlowDTO flow) {
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

    private void removeFlowElements(FlowDTO flow) {
        if (flow.getId() == null) {
            return;
        }
        Collection<NodeJPA> removedNodes = nodeRepository.findAllByFlowId(flow.getId());
        removedNodes.forEach(
            node -> edgeRepository.deleteAllByNodeFrom(node.getId())
        );
        nodeRepository.deleteAll(removedNodes);
    }
}
