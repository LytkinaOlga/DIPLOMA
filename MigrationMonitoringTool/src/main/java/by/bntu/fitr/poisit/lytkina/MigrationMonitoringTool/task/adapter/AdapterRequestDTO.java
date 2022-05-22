package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdapterRequestDTO {
    private String masterListTable;

    public AdapterRequestDTO(String masterListTable) {
        this.masterListTable = masterListTable;
    }
}
