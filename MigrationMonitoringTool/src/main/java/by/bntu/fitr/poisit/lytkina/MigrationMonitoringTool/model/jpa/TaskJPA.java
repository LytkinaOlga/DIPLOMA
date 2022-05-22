package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
