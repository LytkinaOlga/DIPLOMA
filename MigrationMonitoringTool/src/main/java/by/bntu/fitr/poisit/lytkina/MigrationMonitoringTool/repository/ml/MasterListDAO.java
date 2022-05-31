package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionMasterListDTO;
public interface MasterListDAO {
    String ML_TABLE_PREFIX = "ML_";

    int createMasterListTable(Long executionId, String entitiesTableName, String entityIdColumn);
    void dropMasterListTable(Long executionId);
    void cloneMasterListForAdapter(Long executionId, Long nodeId);
    int mergeMasterListFromAdapter(Long executionId, Long nodeId);
    ExecutionMasterListDTO getMasterList(Long executionId);
}
