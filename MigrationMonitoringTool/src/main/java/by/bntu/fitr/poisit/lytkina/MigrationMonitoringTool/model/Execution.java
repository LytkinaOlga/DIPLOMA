package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class Execution {
    private Long id;
    private Long flowId;
    private String startedBy;
    private Date startDate;
    private Date endDate;
    private Collection<ExecutionNode> nodes;
    private Collection<EdgeJPA> edges;
    private ExecutionStatus status;

    public Execution() {
    }

    public Execution(ExecutionJPA executionJPA) {
        this.id = executionJPA.getId();
        this.flowId = executionJPA.getFlowJPA().getId();
        this.startDate = executionJPA.getStartDate();
        this.endDate = executionJPA.getEndDate();
        this.startedBy = executionJPA.getStartedBy();
        this.status = executionJPA.getStatus();
    }
}
