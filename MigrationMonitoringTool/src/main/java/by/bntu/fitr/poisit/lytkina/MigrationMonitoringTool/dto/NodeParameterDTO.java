package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeParameterJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NodeParameterDTO {
    private String id;
    private String value;

    public NodeParameterDTO(NodeParameterJPA parameterJPA) {
        this.id = parameterJPA.getParameter().getId().toString();
        this.value = parameterJPA.getValue();
    }
}
