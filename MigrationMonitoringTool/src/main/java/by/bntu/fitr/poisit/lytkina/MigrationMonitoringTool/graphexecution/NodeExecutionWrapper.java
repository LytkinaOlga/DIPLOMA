package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionNodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.NodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.Task;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.TransactionalParamUpdater;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.ParamNames.NODE_PARAM_PREFIX;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.MasterListAdapter.SUCCESS_PROCESS_ENTITIES_RESULT_POSTFIX;

@NoArgsConstructor
public class NodeExecutionWrapper implements Runnable {
    private Long flowId;
    private Long executionId;
    private Long nodeId;
    private Task task;
    private Map<String, String> taskParameters;

    @Autowired
    TransactionalParamUpdater paramUpdater;

    @Transactional
    public void run() {
        loadTask();

        preExecute();
        try {
            task.run();
        } catch (Exception e) {
            logger.debug("Node execution failed, node: " + nodeId);
            paramUpdater.updateExecutionNode(executionId, nodeId, ExecutionStatus.FAILED, null, new Date(), -1);
            throw new RuntimeException(
                "Node execution failed, node: " + nodeId
                    + ". \nOriginal message: " + e.getMessage(),
                e
            );
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
        taskParameters.put(Constants.ParamNames.NODE_ID, nodeJPA.getId().toString());

        nodeJPA.getParameters().forEach(parameterJPA -> {
            taskParameters.put(
                NODE_PARAM_PREFIX + parameterJPA.getParameter().getId(),
                parameterJPA.getValue()
            );
        });
        task.setParameters(taskParameters);
    }

    public NodeExecutionWrapper(Task task, Map<String, String> taskParameters) {
        this.task = task;
        this.taskParameters = taskParameters;
    }

    private void preExecute() {
        paramUpdater.updateExecutionNode(executionId, nodeId, ExecutionStatus.RUNNING, new Date(), null, null);
        logger.debug("Started to execute task " + task.getName() + " of node " + nodeId);
    }

    private void postExecute() {
        int processedEntities = -1;
        String processedEntitiesString =
            taskParameters.get(Constants.ParamNames.RESULT_PREFIX + SUCCESS_PROCESS_ENTITIES_RESULT_POSTFIX);
        if (StringUtils.hasText(processedEntitiesString)) {
            processedEntities = Integer.parseInt(processedEntitiesString);
        }
        paramUpdater.updateExecutionNode(executionId, nodeId, ExecutionStatus.SUCCEEDED, null, new Date(), processedEntities);
        logger.debug("Finished execution of task " + task.getName() + " of node " + nodeId);
    }

}
