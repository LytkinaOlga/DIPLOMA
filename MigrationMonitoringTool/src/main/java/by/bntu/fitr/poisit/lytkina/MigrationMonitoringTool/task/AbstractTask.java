package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractTask implements Task {
    protected Map<String, String> taskParameters;
    protected static final int STATE_RUNNING = 1;
    protected static final int STATE_CANCEL = 1;
    protected static final int STATE_COMPLETE = 2;
    protected int state = -1;
    protected CountDownLatch latch = new CountDownLatch(1);

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void setParameters(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;
    }

    public String getName() {
        return taskParameters.get(Constants.ParamNames.NODE_NAME);
    }

    @Override
    public void cancel() {
        state = STATE_CANCEL;
        latch.countDown();
    }

    @Override
    public void forceComplete() {
        state = STATE_COMPLETE;
        latch.countDown();
    }
}
