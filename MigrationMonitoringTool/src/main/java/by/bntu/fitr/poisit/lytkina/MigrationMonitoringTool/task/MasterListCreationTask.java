package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.config.SpringContext;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ml.MasterListDAO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.MasterListCreator.ENTITY_COLUMN_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.MasterListCreator.ENTITY_TABLE_PARAM_ID;

public class MasterListCreationTask extends AbstractTask {
    @Override
    public void run() {
        String executionId = taskParameters.get(Constants.ParamNames.CURRENT_EXECUTION_ID);
        String entityTable = taskParameters.get(Constants.ParamNames.NODE_PARAM_PREFIX + ENTITY_TABLE_PARAM_ID);
        String entityIdColumn = taskParameters.get(Constants.ParamNames.NODE_PARAM_PREFIX + ENTITY_COLUMN_PARAM_ID);
        MasterListDAO dao = SpringContext.getBean(MasterListDAO.class);
        dao.createMasterListTable(Long.valueOf(executionId), entityTable, entityIdColumn);
    }
}
