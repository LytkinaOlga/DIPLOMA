package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import lombok.Data;

import java.util.Date;

@Data
public class ExecutionDTO {
    private Long id;
    private Long flowId;
    private ExecutionStatus executionStatus;
    private Date startDate;

    public ExecutionDTO(ExecutionJPA executionJPA) {
        this.id = executionJPA.getId();
        this.flowId = executionJPA.getFlowJPA().getId();
        this. executionStatus = executionJPA.getStatus();
        this.startDate = executionJPA.getStartDate();
    }
}
