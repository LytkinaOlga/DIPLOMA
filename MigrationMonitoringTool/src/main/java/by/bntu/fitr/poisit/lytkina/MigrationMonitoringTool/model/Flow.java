package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FlowDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
public class Flow {
    private Long id;
    private String name;
    private String author;
    private String description;
    private Date creationDate;
    private Date modificationDate;
    private Collection<NodeJPA> nodes;
    private Collection<EdgeJPA> edges;

    public Flow(FlowJPA flowJPA) {
        this.id = flowJPA.getId();
        this.name = flowJPA.getName();
        this.creationDate = flowJPA.getCreationDate();
        this.nodes = flowJPA.getNodes();
        this.author = flowJPA.getAuthor();
        this.description = flowJPA.getDescription();
        this.modificationDate = flowJPA.getModificationDate();
    }

    public Flow(FlowDTO flowDTO) {
        this.id = flowDTO.getId();
        this.name = flowDTO.getName();
        this.creationDate = flowDTO.getCreationDate();
        this.modificationDate = flowDTO.getModificationDate();
        this.setNodes(mapCollect(flowDTO.getNodes(), NodeJPA::new));
        this.setEdges(mapCollect(flowDTO.getEdges(), EdgeJPA::new));
        this.author = flowDTO.getAuthor();
        this.description = flowDTO.getDescription();
    }

    public void shallowMerge(Flow other) {
        if (other.getName() != null) {
            this.name = other.getName();
        }
        if (other.getCreationDate() != null) {
            this.creationDate = other.getCreationDate();
        }
        if (other.getNodes() != null) {
            this.setNodes(other.getNodes());
        }
        if (other.getEdges() != null) {
            this.setEdges(other.getEdges());
        }
    }
}
