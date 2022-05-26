package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class FullFlowDTO {
    private Long id;
    private String name;

//    @JsonFormat(
//        shape = JsonFormat.Shape.STRING,
//        pattern = "dd-MM-yyyy hh:mm:ss"
//    )
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date creationDate = new Date();

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
