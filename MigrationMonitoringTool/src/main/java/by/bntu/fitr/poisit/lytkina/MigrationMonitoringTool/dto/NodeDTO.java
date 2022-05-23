package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.NodeExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NodeDTO {
    private String id;
    private String name;
    private PositionDTO position;
    private NodeExecutionStatus status;

    public NodeDTO(NodeJPA nodeJPA) {
        this.id = nodeJPA.getId().toString();
        this.name = nodeJPA.getName();
        this.position = new PositionDTO(
            nodeJPA.getX(),
            nodeJPA.getY()
        );
        this.status = nodeJPA.getStatus();
    }
}
