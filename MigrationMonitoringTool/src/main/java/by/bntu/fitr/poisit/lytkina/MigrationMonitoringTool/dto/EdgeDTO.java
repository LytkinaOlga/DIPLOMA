package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import lombok.Data;

@Data
public class EdgeDTO {
    private Long id;
    private Long from;
    private Long to;

    public EdgeDTO(EdgeJPA edgeJPA) {
        this.id = edgeJPA.getId();
        this.from = edgeJPA.getNodeFrom().getId();
        this.to = edgeJPA.getNodeTo().getId();
    }
}
