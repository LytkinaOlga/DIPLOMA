package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.EdgeDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Execution;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
public class ExecutionDTO {
    private String id;
    private String flowId;
    private Date startDate;
    private Date endDate;
    private Collection<ExecutionNodeDTO> nodes;
    private Collection<EdgeDTO> edges;
    private String startedBy;

    public ExecutionDTO(Execution execution) {
        this.id = execution.getId().toString();
        this.flowId = execution.getFlowId().toString();
        this.startDate = execution.getStartDate();
        this.endDate = execution.getEndDate();
        this.nodes = mapCollect(execution.getNodes(), ExecutionNodeDTO::new);
        this.edges = mapCollect(execution.getEdges(), EdgeDTO::new);
        this.startedBy = execution.getStartedBy();
    }
}
