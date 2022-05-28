package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.NodeParameterDTO;
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

    public NodeParameterJPA(Long parameterId, String value) {
        this.parameter = new TaskParameterJPA(parameterId);
        this.value = value;
    }

    public NodeParameterJPA(NodeParameterDTO nodeParameterDTO, Long nodeId) {
        this.parameter = new TaskParameterJPA();
        this.parameter.setId(Long.valueOf(nodeParameterDTO.getParamId()));
        this.node = new NodeJPA();
        this.node.setId(nodeId);
        this.value = nodeParameterDTO.getValue();
    }
}
