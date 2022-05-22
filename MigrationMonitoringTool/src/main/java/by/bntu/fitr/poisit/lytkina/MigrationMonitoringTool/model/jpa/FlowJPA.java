package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy = "flow")
    private Set<NodeJPA> nodes;

    public static FlowJPA shallowCopy(Flow flow) {
        FlowJPA flowJPA = new FlowJPA();
        flowJPA.setId(flow.getId());
        flowJPA.setName(flow.getName());
        flowJPA.setCreationDate(flow.getCreationDate());

        return flowJPA;
    }

    public void shallowMerge(Flow flow) {
        if (!Objects.equals(this.id, flow.getId())) {
            throw new IllegalArgumentException(
                "Can't merge flows with different ids: " + flow.getId() + " to " + this.id
            );
        }
        if (flow.getName() != null) {
            this.name = flow.getName();
        }
        if (flow.getCreationDate() != null) {
            this.creationDate = flow.getCreationDate();
        }
    }

    public FlowJPA(Long id) {
        this.id = id;
    }

    @PrePersist
    void createdAt() {
        this.creationDate = new Date();
    }
}
