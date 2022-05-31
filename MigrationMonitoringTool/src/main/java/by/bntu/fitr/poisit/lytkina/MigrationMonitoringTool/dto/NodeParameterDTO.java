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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String value;

    public NodeParameterDTO(NodeParameterJPA parameterJPA) {
        this.id = parameterJPA.getParameter().getId();
        this.value = parameterJPA.getValue();
    }
}
