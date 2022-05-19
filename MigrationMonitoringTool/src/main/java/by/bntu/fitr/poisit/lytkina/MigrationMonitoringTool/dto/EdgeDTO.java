package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EdgeDTO {
    private String id;
    private String source;
    private String target;

    public EdgeDTO(EdgeJPA edgeJPA) {
        this.id = edgeJPA.getId().toString();
        this.source = edgeJPA.getNodeFrom().getId().toString();
        this.target = edgeJPA.getNodeTo().getId().toString();
    }
}
