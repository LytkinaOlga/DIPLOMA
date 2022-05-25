package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "execution_progress")
@Getter
@Setter
@ToString
@NoArgsConstructor
@IdClass(ExecutionProgressJPAPK.class)
public class ExecutionNodeJPA {
    @Id
    @ManyToOne
    private NodeJPA node;

    @Id
    @ManyToOne
    private ExecutionJPA execution;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private ExecutionStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExecutionNodeJPA that = (ExecutionNodeJPA) o;
        return node != null && Objects.equals(node, that.node)
            && execution != null && Objects.equals(execution, that.execution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, execution);
    }
}
