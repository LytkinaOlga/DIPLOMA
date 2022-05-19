package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.NodeDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flows")
@Data
@NoArgsConstructor
public class FlowJPA {
    @Id
    @GeneratedValue
    @Column(name = "flow_id")
    private Long id;

    private String name;

    @Temporal(TemporalType.TIME)
    private Date creationDate;

    public FlowJPA(Flow flow) {
        this.id = flow.getId();
        this.name = flow.getName();
        this.creationDate = flow.getCreationDate();
    }

    public FlowJPA(Long id) {
        this.id = id;
    }

    @PrePersist
    void createdAt() {
        this.creationDate = new Date();
    }
}
