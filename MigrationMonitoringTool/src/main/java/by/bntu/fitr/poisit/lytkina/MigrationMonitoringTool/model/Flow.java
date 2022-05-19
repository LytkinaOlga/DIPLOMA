package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FullFlowDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@Data
public class Flow {
    private Long id;
    private String name;
    private Date creationDate;
    private Collection<NodeJPA> nodes;
    private Collection<EdgeJPA> edges;

    public Flow(FlowJPA flowJPA) {
        this.id = flowJPA.getId();
        this.name = flowJPA.getName();
        this.creationDate = flowJPA.getCreationDate();
    }

    public Flow(FullFlowDTO flowDTO) {
        this.id = flowDTO.getId();
        this.name = flowDTO.getName();
        this.creationDate = flowDTO.getCreationDate();
        this.setNodes(mapCollect(flowDTO.getNodes(), NodeJPA::new));
        this.setEdges(mapCollect(flowDTO.getEdges(), EdgeJPA::new));
    }
}
