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

    public NodeJPA(Long id) {
        this.id = id;
    }

    public NodeJPA(NodeDTO nodeDTO) {
        this.id = nodeDTO.getId();
        this.name = nodeDTO.getName();
    }

    public void setFlow(FlowJPA flow) {
        this.flow = flow;
    }

    public void setFlow(Long id) {
        this.flow = new FlowJPA(id);
    }
}
