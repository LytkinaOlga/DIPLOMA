package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.config.SpringContext;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml.MasterListDAO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.AbstractTask;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml.MasterListDAO.ML_TABLE_PREFIX;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.RandomFailingAdapter.SUCCESS_RATE_PARAM_ID;

public class RandomFailingAdapter extends AbstractTask {
    @Override
    public void run() {

        try {
            String executionId = taskParameters.get(Constants.ParamNames.CURRENT_EXECUTION_ID);
            String nodeId = taskParameters.get(Constants.ParamNames.NODE_ID);
            String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
            String successRate = taskParameters.get(Constants.ParamNames.NODE_PARAM_PREFIX + SUCCESS_RATE_PARAM_ID);
            if (!StringUtils.hasText(successRate)) {
                successRate = "0.5";
            }

            MasterListDAO dao = SpringContext.getBean(MasterListDAO.class);
            dao.cloneMasterListForAdapter(Long.valueOf(executionId), Long.valueOf(nodeId));

            String adapterTableName = ML_TABLE_PREFIX + executionId + "_" + nodeId;
            JdbcTemplate jdbcTemplate = SpringContext.getBean(JdbcTemplate.class);
            String dbURL = jdbcTemplate.getDataSource().getConnection().getMetaData().getURL();

            int count;
            if (dbURL.contains("oracle")) {
                count = jdbcTemplate.update(
                    "MERGE INTO " + adapterTableName + " x\n" +
                        "USING (\n" +
                        "    SELECT entity_id, status, case when (status = 'FAILED') \n"
                        + "then 'Failed by random failing adapter ''" + nodeName + "'' (" + nodeId + ")' else null end error_message FROM (\n" +
                        "        select entity_id, case when (dbms_random.value > 0.5) then 'FAILED' else 'SUCCEED' end status\n" +
                        "        from " + adapterTableName + "\n" +
                        "    )\n" +
                        ") y\n" +
                        "ON (x.entity_id  = y.entity_id)\n" +
                        "WHEN MATCHED THEN UPDATE SET x.status = y.status, x.error_message = y.error_message"
                );
            } else if (dbURL.contains("postgres")) {
                count = jdbcTemplate.update(
                    "insert into " + adapterTableName + "\n" +
                        "select entity_id, status, case when status = 'FAILED' " +
                        "then 'Failed by random failing adapter ''" + nodeName + "'' (" + nodeId + ")' else null end error_message\n" +
                        "from (select entity_id, case when (random() > " + successRate + ") then 'FAILED' else 'SUCCEED' end status\n" +
                        "      from " + adapterTableName + ") f\n" +
                        "on conflict (entity_id) do update set status = excluded.status, error_message = excluded.error_message"
                );
            } else {
                logger.error("Unsupported db type, URL: " + dbURL);
                count = 0;
            }

            logger.debug("Updated {} rows in adapter table {}", count, adapterTableName);

            dao.mergeMasterListFromAdapter(Long.valueOf(executionId), Long.valueOf(nodeId));
        } catch (Exception e ) {
            logger.error("Failed", e);
            throw new RuntimeException(e);
        }
    }
}
