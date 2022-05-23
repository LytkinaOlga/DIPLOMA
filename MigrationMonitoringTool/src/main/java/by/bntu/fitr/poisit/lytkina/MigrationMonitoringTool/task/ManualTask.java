package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

public class ManualTask extends AbstractTask {

    @Override
    public void run() {
        state = STATE_RUNNING;

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (state == STATE_COMPLETE) {
            logger.debug("Task {} completed", getName());
        } else {
            logger.debug("Task {} cancelled", getName());
            throw new TaskCancelException(this);
        }
    }

}
