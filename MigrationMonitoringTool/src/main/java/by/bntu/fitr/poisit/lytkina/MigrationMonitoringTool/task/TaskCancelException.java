package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import java.text.MessageFormat;

public class TaskCancelException extends RuntimeException {
    private final Task task;

    public TaskCancelException(Task task) {
        this.task = task;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Task {0} cancelled, ", task.getName());
    }
}
