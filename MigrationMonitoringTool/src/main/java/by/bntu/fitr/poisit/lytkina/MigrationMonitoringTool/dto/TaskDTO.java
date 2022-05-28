package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.TaskJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
@NoArgsConstructor
public class TaskDTO {
    private String id;
    private String name;
    private List<TaskParameterDTO> parameters;

    public TaskDTO(TaskJPA taskJPA) {
        this.id = taskJPA.getId().toString();
        this.name = taskJPA.getName();
        this.parameters = mapCollect(taskJPA.getTaskParameters(), TaskParameterDTO::new);
    }
}
