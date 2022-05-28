package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class NodePositionDTO {
    @PositiveOrZero
    private double x;
    @PositiveOrZero
    private double y;

    public NodePositionDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
