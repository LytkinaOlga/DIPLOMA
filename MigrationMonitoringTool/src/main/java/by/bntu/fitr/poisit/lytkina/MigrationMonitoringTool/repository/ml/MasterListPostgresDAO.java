package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnExpression("'${spring.datasource.url}'.startsWith('jdbc:postgresql:')")
public class MasterListPostgresDAO implements MasterListDAO {
    private static final Logger logger = LoggerFactory.getLogger(MasterListPostgresDAO.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int createMasterListTable(Long executionId, String entitiesTableName, String entityIdColumn) {
        String mlTableName = ML_TABLE_PREFIX + executionId;
        logger.trace("Creating master list table '{}'", mlTableName);

        jdbcTemplate.execute(
            "create table " + mlTableName
                + "(entity_id numeric, status text, error_message text, primary key (entity_id))"
        );
        logger.trace("Created master list table '{}'", mlTableName);
        int count = jdbcTemplate.update(
            "insert into " + mlTableName + " (entity_id, status) \n"
            + "select " + entityIdColumn + ", 'OK' from " + entitiesTableName
        );
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
            + " (entity_id numeric, status text, error_message text, primary key (entity_id))"
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
            "insert into " + mlTableName + "\n" +
                "select entity_id, status, error_message\n" +
                "from ( select * from " + mlAdapterTableName + " where status = 'FAILED') t\n" +
                "on conflict (entity_id) do update set\n" +
                "  status = case when (" + mlTableName + ".status = 'OK') then excluded.status else " + mlTableName + ".status end,\n" +
                "  error_message = case when (" + mlTableName + ".error_message is null) then " +
                "excluded.error_message else " + mlTableName + ".error_message || ';' || excluded.error_message end"
        );
        logger.debug("Merged {} rows from adapter master list table '{}'", count, mlAdapterTableName);

        count = jdbcTemplate.queryForObject(
            "select count(1) from " + mlAdapterTableName + " x\n"
                + "where status = 'OK'", Integer.class
        );
        logger.debug("Succeeded entities count: {}", count);

//        jdbcTemplate.execute("drop table " + mlAdapterTableName);
        logger.debug("Dropped adapter master list table '{}' ", mlAdapterTableName);
        return count;
    }
}
