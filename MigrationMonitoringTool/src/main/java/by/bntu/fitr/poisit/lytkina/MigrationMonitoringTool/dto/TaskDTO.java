package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.TaskJPA;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
@NoArgsConstructor
public class TaskDTO {
    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private List<TaskParameterDTO> parameters;

    public TaskDTO(TaskJPA taskJPA) {
        this.id = taskJPA.getId();
        this.name = taskJPA.getName();
        this.parameters = mapCollect(taskJPA.getTaskParameters(), TaskParameterDTO::new);
    }
}
