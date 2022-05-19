package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionDTO {
    private double x;
    private double y;

    public PositionDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
