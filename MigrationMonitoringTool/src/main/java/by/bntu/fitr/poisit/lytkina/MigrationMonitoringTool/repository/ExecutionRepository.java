package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionMasterListDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Execution;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.ExecutionNode;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionNodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionNodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml.MasterListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Component
public class ExecutionRepository {
    @Autowired
    FlowRepository flowRepository;

    @Autowired
    ExecutionJPARepository executionJPARepository;

    @Autowired
    ExecutionNodeJPARepository executionNodeJPARepository;

    @Autowired
    MasterListDAO masterListDAO;

    public Optional<Execution> findById(Long executionId) {
        Optional<ExecutionJPA> executionJPAOptional = executionJPARepository.findById(executionId);
        if (!executionJPAOptional.isPresent()) {
            return Optional.empty();
        }
        ExecutionJPA executionJPA = executionJPAOptional.get();

        Long flowId = executionJPA.getFlowJPA().getId();
        Flow flow = flowRepository.findById(executionJPA.getFlowJPA().getId())
            .orElseThrow(() -> new RuntimeException("Flow of execution " + executionId + " not found: " + flowId));

        Collection<ExecutionNodeJPA> executionProgresses = executionNodeJPARepository.findAllByExecutionId(executionId);
        Collection<ExecutionNode> executionNodes = executionProgresses.stream().map(executionProgress -> {
            ExecutionNode executionNode = new ExecutionNode();
            executionNode.setNodeId(executionProgress.getNode().getId());
            executionNode.setNodeName(executionProgress.getNode().getName());
            executionNode.setExecutionId(executionId);
            executionNode.setStatus(executionProgress.getStatus());
            executionNode.setStartDate(executionProgress.getStartDate());
            executionNode.setEndDate(executionProgress.getEndDate());
            executionNode.setSuccessfullyProcessedEntities(executionProgress.getSuccessfullyProcessedEntities());
            return executionNode;
        }).collect(Collectors.toList());

        Execution execution = new Execution();
        execution.setId(executionId);
        execution.setFlowId(flowId);
        execution.setStartDate(executionJPA.getStartDate());
        execution.setEndDate(executionJPA.getEndDate());
        execution.setNodes(executionNodes);
        execution.setEdges(flow.getEdges());
        execution.setStatus(executionJPA.getStatus());
        execution.setErrorMessage(executionJPA.getErrorMessage());

        return Optional.of(execution);
    }

    public ExecutionMasterListDTO getExecutionMasterList(Long executionId) {
        return masterListDAO.getMasterList(executionId);
    }

    public Execution instantiateByFlow(Long flowId) {
        Flow flow = flowRepository.findById(flowId)
            .orElseThrow(() -> new RuntimeException("Flow not found: " + flowId));;

        ExecutionJPA executionJPA = new ExecutionJPA();
        executionJPA.setStatus(ExecutionStatus.NOT_STARTED);
        executionJPA.setFlowJPA(new FlowJPA(flowId));

        Collection<NodeJPA> flowNodes = flow.getNodes();
        Collection<ExecutionNodeJPA> executionNodesJPA = flowNodes.stream().map(flowNode -> {
            ExecutionNodeJPA executionNodeJPA = new ExecutionNodeJPA();
            executionNodeJPA.setNode(flowNode);
            executionNodeJPA.setExecution(executionJPA);
            executionNodeJPA.setStatus(ExecutionStatus.NOT_STARTED);

            return executionNodeJPA;
        }).collect(Collectors.toList());

        executionJPARepository.save(executionJPA);
        executionNodeJPARepository.saveAll(executionNodesJPA);

        Execution execution = new Execution(executionJPA);
        execution.setNodes(mapCollect(executionNodesJPA, ExecutionNode::new));
        execution.setEdges(flow.getEdges());

        return execution;
    }
}
