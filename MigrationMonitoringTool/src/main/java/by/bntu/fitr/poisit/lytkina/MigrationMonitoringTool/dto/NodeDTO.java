package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
@NoArgsConstructor
public class NodeDTO {
    @PositiveOrZero
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    @Valid
    private NodePositionDTO position;

    private String taskId;

    @Valid
    private List<NodeParameterDTO> nodeParametersDTO;

    public NodeDTO(NodeJPA nodeJPA) {
        this.id = nodeJPA.getId();
        this.name = nodeJPA.getName();
        this.position = new NodePositionDTO(
            nodeJPA.getX(),
            nodeJPA.getY()
        );
        this.taskId = nodeJPA.getTask().getId().toString();
        this.nodeParametersDTO = mapCollect(nodeJPA.getParameters(), NodeParameterDTO::new);
    }

    @JsonProperty("parameters")
    public List<NodeParameterDTO> getNodeParametersDTO() {
        if (nodeParametersDTO == null) {
            nodeParametersDTO = new ArrayList<>();
        }
        return nodeParametersDTO;
    }


}
