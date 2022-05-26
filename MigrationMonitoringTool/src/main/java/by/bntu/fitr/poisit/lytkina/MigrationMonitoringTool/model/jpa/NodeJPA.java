package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.NodeDTO;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "nodes")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskJPA task;

    @ManyToOne
    @JoinColumn(name = "executionId")
    private ExecutionJPA execution;

    @OneToMany(mappedBy = "nodeFrom")
    @ToString.Exclude
    private Set<EdgeJPA> outgoingEdges;

    @OneToMany(mappedBy = "nodeTo")
    @ToString.Exclude
    private Set<EdgeJPA> incomingEdges;

    @Column(name = "position_x")
    private double x = 100.1;
    @Column(name = "position_y")
    private double y = 200.2;

    @OneToMany(mappedBy = "node", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<NodeParameterJPA> parameters;

    public NodeJPA(Long id) {
        this.id = id;
    }

    public NodeJPA(NodeDTO nodeDTO) {
        this.id = Long.valueOf(nodeDTO.getId());
        this.name = nodeDTO.getName();
        this.task = new TaskJPA(Long.valueOf(nodeDTO.getTaskId()));
        if (nodeDTO.getPosition() != null) {
            this.setX(nodeDTO.getPosition().getX());
            this.setY(nodeDTO.getPosition().getY());
        }
    }

    public static NodeJPA copyWithoutFlowAndEdgesAndId(NodeJPA orig) {
        NodeJPA newNode =new NodeJPA();
        newNode.setName(orig.getName());
        newNode.setX(orig.getX());
        newNode.setY(orig.getY());
        newNode.setTask(orig.getTask());

        return newNode;
    }

    public void setFlow(FlowJPA flow) {
        this.flow = flow;
    }
}
