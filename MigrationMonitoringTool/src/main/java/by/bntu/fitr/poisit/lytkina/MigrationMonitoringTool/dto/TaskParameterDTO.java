package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.TaskParameterJPA;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class TaskParameterDTO {
    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;

    public TaskParameterDTO(TaskParameterJPA taskParameterJPA) {
        this.id = taskParameterJPA.getId();
        this.name = taskParameterJPA.getName();
    }
}
