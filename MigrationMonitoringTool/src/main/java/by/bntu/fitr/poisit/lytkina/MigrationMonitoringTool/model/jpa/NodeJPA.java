package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.NodeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "nodes")
@Data
@NoArgsConstructor
public class NodeJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "node_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "flow_id")
    private FlowJPA flow;

    @Column(name = "position_x")
    private double x;
    @Column(name = "position_y")
    private double y;

    public NodeJPA(Long id) {
        this.id = id;
    }

    public NodeJPA(NodeDTO nodeDTO) {
        this.id = nodeDTO.getId();
        this.name = nodeDTO.getName();
        if (nodeDTO.getPosition() != null) {
            this.setX(nodeDTO.getPosition().getX());
            this.setY(nodeDTO.getPosition().getY());
        }
    }

    public NodeJPA(NodeJPA orig) {
        this.setId(orig.getId());
        this.setName(orig.getName());
        this.setFlow(orig.getFlow());
        this.setX(orig.getX());
        this.setY(orig.getY());
    }

    public void setFlow(FlowJPA flow) {
        this.flow = flow;
    }

    public void setFlow(Long id) {
        this.flow = new FlowJPA(id);
    }

}
