package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import java.util.Map;

public interface Task {
    void run(Map<String, String> taskParameters);
    void cancel();

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
