package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FlowPreviewDTO {
    private Long id;
    private String name;
    private String author;
    private String description;
    private Date creationDate;
    private Date modificationDate;

    public FlowPreviewDTO(FlowJPA flow) {
        this.id = flow.getId();
        this.name = flow.getName();
        this.creationDate = flow.getCreationDate();
    }
}
