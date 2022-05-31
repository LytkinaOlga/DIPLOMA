package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.ExecutionNode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Data
@NoArgsConstructor
public class ExecutionNodeDTO {
    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long nodeId;
    private String nodeName;
    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long executionId;
    private ExecutionStatus status;
    private Date startDate;
    private Date endDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer successfullyProcessedEntities;

    public ExecutionNodeDTO(ExecutionNode executionNode) {
        this.nodeId = executionNode.getNodeId();
        this.nodeName = executionNode.getNodeName();
        this.executionId = executionNode.getExecutionId();
        this.status = executionNode.getStatus();
        this.startDate = executionNode.getStartDate();
        this.endDate = executionNode.getEndDate();
        
        if (executionNode.getSuccessfullyProcessedEntities() >= 0) {
            this.successfullyProcessedEntities = executionNode.getSuccessfullyProcessedEntities();
        } else {
            successfullyProcessedEntities = null;
        }
    }
}
