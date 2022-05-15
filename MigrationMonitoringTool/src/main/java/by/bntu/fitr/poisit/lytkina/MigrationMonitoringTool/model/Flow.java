package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import javax.persistence.*;

@Entity
@Table(name = "flows")
public class Flow {
    private Long id;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "flow_id")
    public Long getId() {
        return id;
    }

    @Column(name = "title")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
