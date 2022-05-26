package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class TaskJPA {
    @Id
    private Long id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "name")
    private String name;

    @OneToMany(
        mappedBy = "task",
        cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private Collection<TaskParameterJPA> taskParameters;

    public TaskJPA(Long id) {
        this.id = id;
    }
}
