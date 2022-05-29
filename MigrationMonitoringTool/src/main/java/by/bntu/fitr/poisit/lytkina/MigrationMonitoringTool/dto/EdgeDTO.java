package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class EdgeDTO {
    private String id; // can be auto-generated as string by frontend

    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long source;

    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long target;

    public EdgeDTO(EdgeJPA edgeJPA) {
        this.id = edgeJPA.getId().toString();
        this.source = edgeJPA.getNodeFrom();
        this.target = edgeJPA.getNodeTo();
    }
}
