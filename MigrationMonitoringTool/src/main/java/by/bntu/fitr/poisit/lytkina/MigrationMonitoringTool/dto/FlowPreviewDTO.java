package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;

public class FlowPreviewDTO {
    private Long id;
    private String name;
    //todo: creation date

    public FlowPreviewDTO(FlowJPA flow) {
        this.id = flow.getId();
        this.name = flow.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
