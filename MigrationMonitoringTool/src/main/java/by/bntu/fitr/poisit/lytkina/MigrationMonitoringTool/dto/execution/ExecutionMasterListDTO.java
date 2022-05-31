package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExecutionMasterListDTO {
    private List<ExecutionMasterListEntityDTO> entities;

    public ExecutionMasterListDTO(List<ExecutionMasterListEntityDTO> entities) {
        this.entities = entities;
    }
}
