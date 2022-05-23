package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class TaskJPA {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "name")
    private String name;

    @OneToMany(
        mappedBy = "task",
        cascade = CascadeType.ALL
    )
    private Collection<TaskParameterJPA> taskParameters;
}
