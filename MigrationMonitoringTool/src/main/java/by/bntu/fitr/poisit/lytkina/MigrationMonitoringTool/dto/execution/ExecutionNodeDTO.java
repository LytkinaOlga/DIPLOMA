package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.ExecutionNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ExecutionNodeDTO {
    private Long nodeId;
    private String nodeName;
    private Long executionId;
    private ExecutionStatus status;
    private Date startDate;
    private Date endDate;

    public ExecutionNodeDTO(ExecutionNode executionNode) {
        this.nodeId = executionNode.getNodeId();
        this.nodeName = executionNode.getNodeName();
        this.executionId = executionNode.getExecutionId();
        this.status = executionNode.getStatus();
        this.startDate = executionNode.getStartDate();
        this.endDate = executionNode.getEndDate();
    }
}
