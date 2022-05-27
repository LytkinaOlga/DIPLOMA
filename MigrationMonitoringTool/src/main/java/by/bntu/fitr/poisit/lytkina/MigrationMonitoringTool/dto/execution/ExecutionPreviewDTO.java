package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ExecutionPreviewDTO {
    private String id;
    private String flowId;
    private ExecutionStatus status;
    private Date startDate;
    private Date endDate;

    public ExecutionPreviewDTO(ExecutionJPA executionJPA) {
        this.id = executionJPA.getId().toString();
        this.flowId = executionJPA.getFlowJPA().getId().toString();
        this.status = executionJPA.getStatus();
        this.startDate = executionJPA.getStartDate();
        this.endDate = executionJPA.getEndDate();
    }
}
