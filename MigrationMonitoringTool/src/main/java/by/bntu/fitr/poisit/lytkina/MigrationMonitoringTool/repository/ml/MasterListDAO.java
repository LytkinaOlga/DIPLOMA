package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml;

public interface MasterListDAO {
    String ML_TABLE_PREFIX = "ML_";

    void createMasterListTable(Long executionId, String entitiesTableName, String entityIdColumn);
    void dropMasterListTable(Long executionId);
    void cloneMasterListForAdapter(Long executionId, Long nodeId);
    void mergeMasterListFromAdapter(Long executionId, Long nodeId);
}
