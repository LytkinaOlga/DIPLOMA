package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.NodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.Task;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.MASTER_LIST_TABLE_PREFIX;

@NoArgsConstructor
public class NodeExecutionWrapper implements Runnable {
    private ExecutionGraph executionGraph;
    private GraphNode graphNode;
    private Task task;
    private Map<String, String> taskParameters;

    @Transactional
    public void run() {
        loadTask();

        preExecute();
        task.run(taskParameters);
        postExecute();
    }

    public static final Logger logger = LoggerFactory.getLogger(NodeExecutionWrapper.class);

    @Autowired
    NodeJPARepository nodeRepository;

    public NodeExecutionWrapper(ExecutionGraph executionGraph, GraphNode graphNode) {
        this.executionGraph = executionGraph;
        this.graphNode = graphNode;
    }

    @Transactional
    protected void loadTask() {
        Long nodeId = graphNode.getNodeId();

        NodeJPA nodeJPA = nodeRepository.findById(nodeId).orElseThrow(
            () -> new IllegalArgumentException("No node with id " + nodeId + "present")
        );

        try {
            String taskClassName = nodeJPA.getTaskJPA().getClassName();
            Class<?> clazz = Class.forName(taskClassName);
            Constructor<?> constructor = clazz.getConstructor();
            task = (Task)constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException
                 | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate task", e);
        }

        taskParameters = new HashMap<>();
        taskParameters.put(Constants.ParamNames.CURRENT_EXECUTION_ID, executionGraph.getExecutionId().toString());
        taskParameters.put(Constants.ParamNames.NODE_NAME, nodeJPA.getName());
        taskParameters.put(Constants.ParamNames.ADAPTER_URL, "http://localhost:8081");
    }

    private void executeTask() {
        Long executionId = executionGraph.getExecutionId();
        Long nodeId = graphNode.getNodeId();

        NodeJPA nodeJPA = nodeRepository.findById(graphNode.getNodeId()).get();

        task = null;
        taskParameters = null;
    }

    public NodeExecutionWrapper(Task task, Map<String, String> taskParameters) {
        this.task = task;
        this.taskParameters = taskParameters;
    }



    private void preExecute() {
        logger.debug("Started to execute task " + task.getName() + " of node " + graphNode.getNodeId());
    }

    private void postExecute() {
        logger.debug("Finished execution of task " + task.getName() + " of node " + graphNode.getNodeId());
    }
}
