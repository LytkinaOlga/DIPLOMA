package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.ExecutionNode;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionNodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionProgressJPAPK;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionNodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.NodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.Task;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class NodeExecutionWrapper implements Runnable {
    private Long flowId;
    private Long executionId;
    private Long nodeId;
    private Task task;
    private Map<String, String> taskParameters;

    @Transactional
    public void run() {
        loadTask();

        preExecute();
        try {
            task.run();
        } catch (Exception e) {
            logger.debug("Node execution failed, node: " + nodeId);
            updateNode(ExecutionStatus.FAILED, null, new Date());
            throw new RuntimeException("Node execution failed, node: " + nodeId, e);
        }
        postExecute();
    }

    public static final Logger logger = LoggerFactory.getLogger(NodeExecutionWrapper.class);

    @Autowired
    NodeJPARepository nodeRepository;

    @Autowired
    ExecutionNodeJPARepository executionNodeJPARepository;

    public NodeExecutionWrapper(ExecutionGraph executionGraph, GraphNode graphNode, Long executionId) {
        this.flowId = executionGraph.getFlowId();
        this.nodeId = graphNode.getNodeId();
        this.executionId = executionId;
    }

    public void forceCompleteTask() {
        task.forceComplete();
    }

    @Transactional
    protected void loadTask() {
        NodeJPA nodeJPA = nodeRepository.findById(nodeId).orElseThrow(
            () -> new IllegalArgumentException("No node with id " + nodeId + "present")
        );

        try {
            String taskClassName = nodeJPA.getTask().getClassName();
            Class<?> clazz = Class.forName(taskClassName);
            Constructor<?> constructor = clazz.getConstructor();
            task = (Task)constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException
                 | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate task", e);
        }

        taskParameters = new HashMap<>();
        taskParameters.put(Constants.ParamNames.CURRENT_FLOW_ID, flowId.toString());
        taskParameters.put(Constants.ParamNames.CURRENT_EXECUTION_ID, executionId.toString());
        taskParameters.put(Constants.ParamNames.NODE_NAME, nodeJPA.getName());
        taskParameters.put(Constants.ParamNames.ADAPTER_URL, "http://localhost:8081");
        task.setParameters(taskParameters);
    }

    public NodeExecutionWrapper(Task task, Map<String, String> taskParameters) {
        this.task = task;
        this.taskParameters = taskParameters;
    }

    private void preExecute() {
        updateNode(ExecutionStatus.RUNNING, new Date(), null);
        logger.debug("Started to execute task " + task.getName() + " of node " + nodeId);
    }

    private void postExecute() {
        updateNode(ExecutionStatus.SUCCEEDED, null, new Date());
        logger.debug("Finished execution of task " + task.getName() + " of node " + nodeId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void updateNode(ExecutionStatus status, Date startDate, Date endDate) {
        ExecutionNodeJPA executionNode = executionNodeJPARepository.findById(new ExecutionProgressJPAPK(executionId, nodeId))
            .orElseThrow(() -> new RuntimeException("Failed to get execution node: " + nodeId + " of execution " + executionId));
        if (status != null) {
            executionNode.setStatus(status);
        }
        if (startDate != null) {
            executionNode.setStartDate(startDate);
        }
        if (endDate != null) {
            executionNode.setEndDate(endDate);
        }
        executionNodeJPARepository.save(executionNode);
    }
}
