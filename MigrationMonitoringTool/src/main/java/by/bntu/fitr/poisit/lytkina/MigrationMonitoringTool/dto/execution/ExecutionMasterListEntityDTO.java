package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExecutionMasterListEntityDTO {
    private String entityId;
    private String status;
    private String failedOn;
    private String errorMessage;

    public ExecutionMasterListEntityDTO(String entityId, String status, String failedOn, String errorMessage) {
        this.entityId = entityId;
        this.status = status;
        this.failedOn = failedOn;
        this.errorMessage = errorMessage;
    }
}
