package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NodeDTO {
    private Long id;
    private String name;
    private PositionDTO position;

    public NodeDTO(NodeJPA nodeJPA) {
        this.id = nodeJPA.getId();
        this.name = nodeJPA.getName();
        this.position = new PositionDTO(
            nodeJPA.getX(),
            nodeJPA.getY()
        );
    }
}
