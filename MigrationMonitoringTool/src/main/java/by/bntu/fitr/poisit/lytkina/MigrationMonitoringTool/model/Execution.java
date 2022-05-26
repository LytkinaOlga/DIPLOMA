package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FullFlowDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
public class Execution {
    private Long id;
    private Long flowId;
    private Date startDate;
    private Date endDate;
    private Collection<ExecutionNode> nodes;
    private Collection<EdgeJPA> edges;

    public Execution() {
    }

    public Execution(ExecutionJPA executionJPA) {
        this.id = executionJPA.getId();
        this.flowId = executionJPA.getFlowJPA().getId();
        this.startDate = executionJPA.getStartDate();
        this.endDate = executionJPA.getEndDate();
    }
}
