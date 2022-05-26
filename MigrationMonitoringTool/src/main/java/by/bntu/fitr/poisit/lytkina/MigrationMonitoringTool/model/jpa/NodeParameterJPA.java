package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.*;

import javax.persistence.*;

@IdClass(NodeParameterJPAPK.class)
@Table(name = "node_params")
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NodeParameterJPA {
    @Id
    @ManyToOne
    private NodeJPA node;

    @Id
    @ManyToOne
    private TaskParameterJPA parameter;

    private String value;

    public NodeParameterJPA(NodeJPA node, TaskParameterJPA parameter, String value) {
        this.node = node;
        this.parameter = parameter;
        this.value = value;
    }
}
