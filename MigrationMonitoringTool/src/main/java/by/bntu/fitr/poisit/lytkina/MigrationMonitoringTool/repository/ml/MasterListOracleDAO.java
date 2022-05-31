package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionMasterListDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionMasterListEntityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ConditionalOnExpression("'${spring.datasource.url}'.startsWith('jdbc:oracle:')")
public class MasterListOracleDAO implements MasterListDAO {
    private static final Logger logger = LoggerFactory.getLogger(MasterListOracleDAO.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int createMasterListTable(Long executionId, String entitiesTableName, String entityIdColumn) {
        String mlTableName = ML_TABLE_PREFIX + executionId;
        logger.trace("Creating master list table '{}'", mlTableName);

        jdbcTemplate.execute(
            "create table " + mlTableName
                + "(entity_id number, status varchar2(20), failed_on varchar(200), error_message varchar2(4000), "
                + "CONSTRAINT " + mlTableName + "_PK  PRIMARY KEY (entity_id))"
        );
        logger.trace("Created master list table '{}'", mlTableName);
        int count = jdbcTemplate.update(
            "insert into " + mlTableName + " (entity_id, status, failed_on, error_message) \n"
                + "select " + entityIdColumn + ", 'OK', '', '' from " + entitiesTableName
        );
        jdbcTemplate.execute("COMMIT");
        logger.debug("Created master list table '{}' with {} rows", mlTableName, count);
        return count;
    }

    @Override
    public void dropMasterListTable(Long executionId) {
        String mlTableName = ML_TABLE_PREFIX + executionId;
        logger.trace("Dropping master list table '{}'", mlTableName);
        jdbcTemplate.execute("drop table " + mlTableName);
        logger.debug("Dropped master list table '{}'", mlTableName);
    }

    public void cloneMasterListForAdapter(Long executionId, Long nodeId) {
        String mlAdapterTableName = ML_TABLE_PREFIX + executionId + "_" + nodeId;
        String mlTableName = ML_TABLE_PREFIX + executionId;

        logger.trace("Creating adapter master list table '{}'", mlAdapterTableName);
        jdbcTemplate.execute(
            "create table " + mlAdapterTableName
                + " (entity_id number, status varchar(20), error_message varchar2(4000), "
                + "CONSTRAINT " + mlAdapterTableName + "_PK  PRIMARY KEY (entity_id))"
        );
        logger.trace("Created adapter master list table '{}'", mlAdapterTableName);

        int count = jdbcTemplate.update(
            "insert into " + mlAdapterTableName + " (entity_id) \n"
                + " select entity_id from " + mlTableName + " where status is null or status != 'FAILED'"
        );
        logger.debug("Created master list table '{}' with {} rows", mlAdapterTableName, count);

    }

    public int mergeMasterListFromAdapter(Long executionId, Long nodeId)  {
        String mlAdapterTableName = ML_TABLE_PREFIX + executionId + "_" + nodeId;
        String mlTableName = ML_TABLE_PREFIX + executionId;

        int count = jdbcTemplate.update(
            "MERGE INTO " + mlTableName + " x\n" +
                "USING (SELECT entity_id, status, error_message FROM " + mlAdapterTableName + " where status = 'FAILED') y\n" +
                "ON (x.entity_id  = y.entity_id)\n" +
                "WHEN MATCHED THEN UPDATE SET x.status = 'FAILED'\n" +
                ", x.error_message = x.error_message || y.error_message || ';'\n" +
                ", x.failed_on = x.failed_on || '" + nodeId + ";'"

        );
        logger.debug("Merged {} rows from adapter master list table '{}'", count, mlAdapterTableName);

        count = jdbcTemplate.queryForObject(
            "select count(1) from " + mlAdapterTableName + " x\n"
                + "where status = 'OK'", Integer.class
        );
        logger.debug("Succeeded entities count: {}", count);

        jdbcTemplate.execute("drop table " + mlAdapterTableName + " CASCADE CONSTRAINTS");
        logger.debug("Dropped adapter master list table '{}' ", mlAdapterTableName);
        return count;
    }


    @Override
    public ExecutionMasterListDTO getMasterList(Long executionId) {
        String mlTableName = ML_TABLE_PREFIX + executionId;
        List<ExecutionMasterListEntityDTO> entities =
            jdbcTemplate.query(
                "select entity_id, status, failed_on, error_message from " + mlTableName,
                (rs, rowNum) ->
                    new ExecutionMasterListEntityDTO(
                        rs.getString("entity_id"),
                        rs.getString("status"),
                        rs.getString("failed_on"),
                        rs.getString("error_message")
                    )
            );
        return new ExecutionMasterListDTO(entities);
    }
}

