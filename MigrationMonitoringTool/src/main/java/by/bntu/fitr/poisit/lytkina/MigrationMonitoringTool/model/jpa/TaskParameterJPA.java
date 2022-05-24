package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_params")
@Data
@NoArgsConstructor
public class TaskParameterJPA {
    @Id
    @GeneratedValue
    private Long id;
}
