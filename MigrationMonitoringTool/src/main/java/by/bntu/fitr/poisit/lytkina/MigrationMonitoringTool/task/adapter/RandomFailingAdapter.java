package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.config.SpringContext;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml.MasterListDAO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml.MasterListPostgresDAO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.AbstractTask;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml.MasterListDAO.ML_TABLE_PREFIX;

public class RandomFailingAdapter extends AbstractTask {
    public static final String SUCCESS_RATE_PARAM_ID = "4";
    @Override
    public void run() {

        try {
            String executionId = taskParameters.get(Constants.ParamNames.CURRENT_EXECUTION_ID);
            String nodeId = taskParameters.get(Constants.ParamNames.NODE_ID);
            String successRate = taskParameters.get(Constants.ParamNames.NODE_PARAM_PREFIX + SUCCESS_RATE_PARAM_ID);
            if (!StringUtils.hasText(successRate)) {
                successRate = "0.5";
            }

            MasterListDAO dao = SpringContext.getBean(MasterListDAO.class);
            dao.cloneMasterListForAdapter(Long.valueOf(executionId), Long.valueOf(nodeId));

            String adapterTableName = ML_TABLE_PREFIX + executionId + "_" + nodeId;
            JdbcTemplate template = SpringContext.getBean(JdbcTemplate.class);
            int count = template.update(
                "insert into " + adapterTableName
                    + " select entity_id, case when (random() > " + successRate + ") then 'FAILED' else 'SUCCEED' end status"
                    + " from " + adapterTableName
                    + " on conflict (entity_id) do update set status = excluded.status"
            );

            logger.debug("Updated {} rows in adapter table {}", count, adapterTableName);

            dao.mergeMasterListFromAdapter(Long.valueOf(executionId), Long.valueOf(nodeId));
        } catch (Exception e ) {
            logger.error("Failed", e);
            throw e;
        }
    }
}
