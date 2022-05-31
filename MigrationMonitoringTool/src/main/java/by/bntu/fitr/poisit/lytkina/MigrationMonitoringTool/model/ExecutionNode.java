package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionNodeDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionNodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ExecutionNode {
    private Long nodeId;
    private String nodeName;
    private Long executionId;
    private ExecutionStatus status;
    private Date startDate;
    private Date endDate;
    private int successfullyProcessedEntities;

    public ExecutionNode(ExecutionNodeJPA executionNodeJPA) {
        this.nodeId = executionNodeJPA.getNode().getId();
        this.executionId = executionNodeJPA.getExecution().getId();
        this.status = executionNodeJPA.getStatus();
        this.startDate = executionNodeJPA.getStartDate();
        this.endDate = executionNodeJPA.getEndDate();
        this.nodeName = executionNodeJPA.getNode().getName();
        this.successfullyProcessedEntities = executionNodeJPA.getSuccessfullyProcessedEntities();
    }
}
