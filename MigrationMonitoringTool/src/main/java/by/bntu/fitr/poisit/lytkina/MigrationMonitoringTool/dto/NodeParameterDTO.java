package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeParameterJPA;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class NodeParameterDTO {
    @PositiveOrZero
    private Long paramId;
    private String value;

    public NodeParameterDTO(NodeParameterJPA parameterJPA) {
        this.paramId = parameterJPA.getParameter().getId();
        this.value = parameterJPA.getValue();
    }
}
