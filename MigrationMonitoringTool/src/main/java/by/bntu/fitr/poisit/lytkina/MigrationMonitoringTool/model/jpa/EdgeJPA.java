package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.EdgeDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
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

    @OneToOne
    @JoinColumn(name = "node_from")
    private NodeJPA nodeFrom;

    @OneToOne
    @JoinColumn(name = "node_to")
    private NodeJPA nodeTo;

    public EdgeJPA(EdgeDTO edgeDTO) {
        this.id = edgeDTO.getId();
        this.nodeFrom = new NodeJPA(edgeDTO.getFrom());
        this.nodeTo = new NodeJPA(edgeDTO.getTo());
    }
}
