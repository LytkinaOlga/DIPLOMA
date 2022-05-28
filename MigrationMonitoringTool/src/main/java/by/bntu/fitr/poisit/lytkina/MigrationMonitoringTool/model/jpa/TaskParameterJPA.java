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
    private Long id;
    private String name;

    @ManyToOne
    private TaskJPA task;

    public TaskParameterJPA(Long id) {
        this.id = id;
    }

    public TaskParameterJPA(Long id, String name, TaskJPA task) {
        this.id = id;
        this.name = name;
        this.task = task;
    }
}
