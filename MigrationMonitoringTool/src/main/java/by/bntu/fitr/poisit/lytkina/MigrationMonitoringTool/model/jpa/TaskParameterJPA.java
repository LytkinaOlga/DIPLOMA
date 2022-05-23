package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "task_params")
@Data
@NoArgsConstructor
public class TaskParameterJPA {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String value;

    @ManyToOne
    private TaskJPA task;

    public TaskParameterJPA(TaskJPA task, String name, String value) {
        this.name = name;
        this.value = value;
        this.task = task;
    }
}
