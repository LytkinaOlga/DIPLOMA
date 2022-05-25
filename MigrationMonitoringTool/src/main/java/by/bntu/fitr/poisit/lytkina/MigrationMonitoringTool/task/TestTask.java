package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import org.springframework.util.StringUtils;

public class TestTask extends AbstractTask {
    private static final String SLEEP_TIME_PARAM_ID = "1";
    private static final String MESSAGE_PARAM_ID = "2";

    @Override
    public void run() {
        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        logger.debug("Task " + nodeName + " started");

        String message = getMessage();
        if (StringUtils.hasText(message)) {
            logger.debug("Task " + nodeName + " message: " + getMessage());

        }

        String sleepTime = getSleepTime();
        if (StringUtils.hasText(sleepTime)) {
            try {
                Thread.sleep(Long.parseLong(sleepTime));
                logger.debug("Task " + nodeName + " slept for " + sleepTime);
            } catch (InterruptedException e) {
                logger.debug("Task " + nodeName + " interrupted");
            }
        }
    }

    @Override
    public void cancel() {
        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        logger.debug("Task " + nodeName + " cancelled");
        Thread.currentThread().interrupt();
    }

    @Override
    public void forceComplete() {
        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        logger.debug("Task " + nodeName + " force completed");
    }

    private String getSleepTime() {
        return taskParameters.get(Constants.ParamNames.NODE_PARAM_PREFIX + SLEEP_TIME_PARAM_ID);
    }

    private String getMessage() {
        return taskParameters.get(Constants.ParamNames.NODE_PARAM_PREFIX + MESSAGE_PARAM_ID);
    }
}
