package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

public class ExecutionProgressJPAPK implements Serializable {
    private Long node;
    private Long execution;

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
