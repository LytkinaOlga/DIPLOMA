package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NodeDTO {
    private String id;
    private String name;
    private NodePositionDTO position;
    private String taskId;

    public NodeDTO(NodeJPA nodeJPA) {
        this.id = nodeJPA.getId().toString();
        this.name = nodeJPA.getName();
        this.position = new NodePositionDTO(
            nodeJPA.getX(),
            nodeJPA.getY()
        );
        this.taskId = nodeJPA.getTask().getId().toString();
    }
}
