package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
@NoArgsConstructor
public class NodeDTO {
    private String id;
    private String name;
    private NodePositionDTO position;
    private String taskId;

    @JsonProperty("parameters")
    private List<NodeParameterDTO> nodeParametersDTO;

    public NodeDTO(NodeJPA nodeJPA) {
        this.id = nodeJPA.getId().toString();
        this.name = nodeJPA.getName();
        this.position = new NodePositionDTO(
            nodeJPA.getX(),
            nodeJPA.getY()
        );
        this.taskId = nodeJPA.getTask().getId().toString();
        this.nodeParametersDTO = mapCollect(nodeJPA.getParameters(), NodeParameterDTO::new);
    }
}
