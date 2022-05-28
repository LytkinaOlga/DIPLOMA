package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class FlowDTO {
    @PositiveOrZero
    private Long id;
    private String name;
    private String author;
    private String description;
    private Date modificationDate;
    private Date creationDate;

    @Valid
    private Collection<NodeDTO> nodes;

    @Valid
    private Collection<EdgeDTO> edges;

    public FlowDTO(Flow flow) {
        this.id = flow.getId();
        this.name = flow.getName();
        this.creationDate = flow.getCreationDate();
        this.modificationDate = flow.getModificationDate();
        this.nodes = flow.getNodes().stream()
            .map(NodeDTO::new)
            .collect(Collectors.toList());

        this.edges = flow.getEdges().stream()
            .map(EdgeDTO::new)
            .collect(Collectors.toList());

        this.author = flow.getAuthor();
        this.description = flow.getDescription();
    }
}
