package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.TaskParameterJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskParameterDTO {
    private String id;
    private String name;

    public TaskParameterDTO(TaskParameterJPA taskParameterJPA) {
        this.id = taskParameterJPA.getId().toString();
        this.name = taskParameterJPA.getName();
    }
}
