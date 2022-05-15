package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;

public class FlowPreview {
    private Long id;
    private String name;
    //todo: creation date

    public FlowPreview(Flow flow) {
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
