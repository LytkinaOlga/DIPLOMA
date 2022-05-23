package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;

public class TestTask extends AbstractTask {
    @Override
    public void run() {
        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        System.out.println("Task " + nodeName + " started");
    }

    @Override
    public void cancel() {
        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        System.out.println("Task " + nodeName + " cancelled");
    }

    @Override
    public void forceComplete() {
        String nodeName = taskParameters.get(Constants.ParamNames.NODE_NAME);
        System.out.println("Task " + nodeName + " force completed");
    }
}
