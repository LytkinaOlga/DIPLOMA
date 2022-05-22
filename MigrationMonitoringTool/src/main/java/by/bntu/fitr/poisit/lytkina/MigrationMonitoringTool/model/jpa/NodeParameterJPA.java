package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@IdClass(NodeParameterJPAPK.class)
@Table(name = "node_params")
@Entity
@NoArgsConstructor
@Data
public class NodeParameterJPA {
    @Id
    @ManyToOne
    private NodeJPA node;

    @Id
    @ManyToOne
    private TaskParameterJPA parameter;

    private String value;
}
