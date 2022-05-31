package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils;

public interface Constants {
    String MASTER_LIST_TABLE_PREFIX = "ML_";

    interface ParamNames {
        String NODE_PARAM_PREFIX = "PARAM_";
        String CURRENT_EXECUTION_ID = "CURRENT_EXECUTION_ID";
        String CURRENT_FLOW_ID = "CURRENT_FLOW_ID";
        String NODE_NAME = "NODE_NAME";
        String NODE_ID = "NODE_ID";
        String RESULT_PREFIX = "RESULT_";
    }

    interface Tasks {
        interface TestTask {
            Long ID = 1L;
            Long DELAY_PARAM_ID = 1L;
            Long MESSAGE_PARAM_ID = 2L;
        }
        interface ManualTask {
            Long ID = 2L;
        }
        interface MasterListCreator {
            Long ID = 3L;
            Long ENTITY_TABLE_PARAM_ID = 5L;
            Long ENTITY_COLUMN_PARAM_ID = 6L;

        }
        interface MasterListAdapter {
            Long ID = 4L;
            Long URL_PARAM_ID = 3L;
            String SUCCESS_PROCESS_ENTITIES_RESULT_POSTFIX = "SUCCESSFULLY_PROCESSED_ENTITIES";

        }
        interface RandomFailingAdapter {
            Long ID = 5L;
            Long SUCCESS_RATE_PARAM_ID = 4L;
        }
        interface Adapter {
            Long ID = 6L;
            Long URL_PARAM_ID = 7L;
        }
    }
}
