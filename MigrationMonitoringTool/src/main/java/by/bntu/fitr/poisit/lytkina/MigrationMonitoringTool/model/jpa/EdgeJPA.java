package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.EdgeDTO;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "edges")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EdgeJPA {
    @Id
    @GeneratedValue
    @Column(name = "edge_id")
    private Long id;

    @Column(name = "node_from")
    private Long nodeFrom;

    @Column(name = "node_to")
    private Long nodeTo;

    public EdgeJPA(EdgeDTO edgeDTO) {
        this.nodeFrom = Long.valueOf(edgeDTO.getSource());
        this.nodeTo = Long.valueOf(edgeDTO.getTarget());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EdgeJPA edgeJPA = (EdgeJPA) o;
        return id != null && Objects.equals(id, edgeJPA.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nodeFrom, nodeTo);
    }
}
