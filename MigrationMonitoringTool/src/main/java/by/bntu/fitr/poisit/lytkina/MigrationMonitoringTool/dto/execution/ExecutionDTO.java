package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.EdgeDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Execution;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.Date;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
public class ExecutionDTO {
    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long flowId;
    private ExecutionStatus status;
    private Date startDate;
    private Date endDate;
    @Valid
    private Collection<ExecutionNodeDTO> nodes;
    @Valid
    private Collection<EdgeDTO> edges;
    private String startedBy;
    private String errorMessage;

    public ExecutionDTO(Execution execution) {
        this.id = execution.getId();
        this.flowId = execution.getFlowId();
        this.startDate = execution.getStartDate();
        this.endDate = execution.getEndDate();
        this.nodes = mapCollect(execution.getNodes(), ExecutionNodeDTO::new);
        this.edges = mapCollect(execution.getEdges(), EdgeDTO::new);
        this.startedBy = execution.getStartedBy();
        this.status = execution.getStatus();
        this.errorMessage = execution.getErrorMessage();
    }
}
