package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "executions")
@Data
@NoArgsConstructor
public class ExecutionJPA {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private FlowJPA flowJPA;

    @Column(name = "status")
    ExecutionStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    private String startedBy;

    @Column(length = 4000)
    private String errorMessage;
}
