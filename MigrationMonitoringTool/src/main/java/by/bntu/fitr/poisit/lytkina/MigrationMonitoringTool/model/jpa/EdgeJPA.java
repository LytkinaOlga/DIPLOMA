package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.EdgeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "edges")
@Data
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
        this.id = Long.valueOf(edgeDTO.getId());
        this.nodeFrom = Long.valueOf(edgeDTO.getSource());
        this.nodeTo = Long.valueOf(edgeDTO.getTarget());
    }
}
