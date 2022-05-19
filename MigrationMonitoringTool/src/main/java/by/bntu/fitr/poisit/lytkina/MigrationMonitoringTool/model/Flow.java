package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class Flow {
    private Long id;
    private String name;
    private Date createdDate;
    private Collection<NodeJPA> nodes;
    private Collection<EdgeJPA> edges;

    public Flow(FlowJPA flowJPA) {
        this.id = flowJPA.getId();
        this.name = flowJPA.getName();
        this.createdDate = flowJPA.getCreatedDate();
    }
}
