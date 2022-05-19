package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class FullFlowDTO {
    private Long id;
    private String name;

    @JsonIgnore
//    @JsonFormat(
//        shape = JsonFormat.Shape.STRING,
//        pattern = "dd-MM-yyyy mich hh:mm:ss"
//    )
    private Date creationDate;

    private Collection<NodeDTO> nodes;

    private Collection<EdgeDTO> edges;

    public FullFlowDTO(Flow flow) {
        this.id = flow.getId();
        this.name = flow.getName();
        this.creationDate = flow.getCreationDate();
        this.nodes = flow.getNodes().stream()
            .map(NodeDTO::new)
            .collect(Collectors.toList());

        this.edges = flow.getEdges().stream()
            .map(EdgeDTO::new)
            .collect(Collectors.toList());

    }
}
