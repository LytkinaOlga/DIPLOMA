package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EdgeDTO {
    private Long id;
    private Long source;
    private Long target;

    public EdgeDTO(EdgeJPA edgeJPA) {
        this.id = edgeJPA.getId();
        this.source = edgeJPA.getNodeFrom().getId();
        this.target = edgeJPA.getNodeTo().getId();
    }
}
