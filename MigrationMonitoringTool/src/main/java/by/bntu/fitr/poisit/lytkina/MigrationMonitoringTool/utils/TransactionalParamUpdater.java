package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionNodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionProgressJPAPK;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionNodeJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class TransactionalParamUpdater {
    @Autowired
    ExecutionNodeJPARepository executionNodeJPARepository;
    @Autowired
    ExecutionJPARepository executionJPARepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateExecutionNode(Long executionId, Long nodeId,
                                       ExecutionStatus status, Date startDate, Date endDate, Integer successfullyProcessedEntities) {
        ExecutionNodeJPA executionNode = executionNodeJPARepository.findById(
            new ExecutionProgressJPAPK(executionId, nodeId)
        ).orElseThrow(
            () -> new RuntimeException("Failed to get execution node: " + nodeId + " of execution " + executionId)
        );
        if (status != null) {
            executionNode.setStatus(status);
        }
        if (startDate != null) {
            executionNode.setStartDate(startDate);
        }
        if (endDate != null) {
            executionNode.setEndDate(endDate);
        }
        if (successfullyProcessedEntities != null) {
            executionNode.setSuccessfullyProcessedEntities(successfullyProcessedEntities);
        }
        executionNodeJPARepository.saveAndFlush(executionNode);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateExecution(Long executionId, ExecutionStatus status, Date startDate, Date endDate) {
        updateExecution(executionId, status, startDate, endDate, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateExecution(Long executionId, ExecutionStatus status, Date startDate, Date endDate, String error_message) {
        ExecutionJPA executionJPA = executionJPARepository.findById(executionId)
            .orElseThrow(() -> new RuntimeException("Failed to get execution " + executionId));
        if (status != null) {
            executionJPA.setStatus(status);
        }
        if (startDate != null) {
            executionJPA.setStartDate(startDate);
        }
        if (endDate != null) {
            executionJPA.setEndDate(endDate);
        }
        if (error_message != null) {
            executionJPA.setErrorMessage(error_message);
        }
        executionJPARepository.save(executionJPA);
    }
}
