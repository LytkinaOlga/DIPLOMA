package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa;

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
    private Date createdDate;

    @PrePersist
    void createdAt() {
        this.createdDate = new Date();
    }
}
