package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.TaskJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class TaskDTO {
    private String id;
    private String name;
    private Map<String, String> parameters;

    public TaskDTO(TaskJPA taskJPA) {
        this.id = taskJPA.getId().toString();
        this.name = taskJPA.getName();
        this.parameters = new HashMap<>();
        taskJPA.getTaskParameters().forEach(p -> {
            parameters.put(p.getName(), "string");
        });
    }
}
