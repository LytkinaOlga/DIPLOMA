package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import java.util.Map;

public interface Task {
    void setParameters(Map<String, String> taskParameters);
    void run();
    void cancel();
    void forceComplete();

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
