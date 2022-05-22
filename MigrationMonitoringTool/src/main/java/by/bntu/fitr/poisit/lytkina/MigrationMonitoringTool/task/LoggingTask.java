package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;

import java.util.Map;

public class LoggingTask implements Task {
    private Map<String, String> taskParameters;

    @Override
    public void run(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;

        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        System.out.println("Task " + nodeName + " started");
    }

    @Override
    public void cancel() {
        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        System.out.println("Task " + nodeName + " cancelled");
    }
}
