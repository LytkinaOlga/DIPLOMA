package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.ExecutionNode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ExecutionNodeDTO {
    private String nodeId;
    private String nodeName;
    private String executionId;
    private ExecutionStatus status;
    private Date startDate;
    private Date endDate;

    public ExecutionNodeDTO(ExecutionNode executionNode) {
        this.nodeId = executionNode.getNodeId().toString();
        this.nodeName = executionNode.getNodeName();
        this.executionId = executionNode.getExecutionId().toString();
        this.status = executionNode.getStatus();
        this.startDate = executionNode.getStartDate();
        this.endDate = executionNode.getEndDate();
    }
}
