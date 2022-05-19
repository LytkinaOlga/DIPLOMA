package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;

@Data
public class NodeDTO {
    private Long id;
    private String name;

    public NodeDTO(NodeJPA nodeJPA) {
        this.id = nodeJPA.getId();
        this.name = nodeJPA.getName();
    }
}
