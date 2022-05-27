package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnExpression("'${spring.datasource.url}'.startsWith('jdbc:oracle:')")
public class MasterListOracleDAO implements MasterListDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createMasterListTable(Long executionId, String entitiesTableName, String entityIdColumn) {
        jdbcTemplate.execute("create table " + ML_TABLE_PREFIX + executionId.toString() + "" +
            "(entity_id numeric, node_id numeric, status varchar2, error_message varchar2)");
    }

    @Override
    public void dropMasterListTable(Long executionId) {

    }

    public void cloneMasterListForAdapter(Long executionId, Long nodeId) {

    }

    public void mergeMasterListFromAdapter(Long executionId, Long nodeId) {

    }
}

