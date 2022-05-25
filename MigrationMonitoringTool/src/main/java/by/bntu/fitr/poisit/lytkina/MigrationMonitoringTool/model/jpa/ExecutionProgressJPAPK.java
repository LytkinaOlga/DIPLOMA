package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class ExecutionProgressJPAPK implements Serializable {
    private Long execution;
    private Long node;

    public ExecutionProgressJPAPK(Long execution, Long node) {
        this.execution = execution;
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExecutionProgressJPAPK that = (ExecutionProgressJPAPK) o;
        return node != null && Objects.equals(node, that.node)
            && execution != null && Objects.equals(execution, that.execution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, execution);
    }
}
