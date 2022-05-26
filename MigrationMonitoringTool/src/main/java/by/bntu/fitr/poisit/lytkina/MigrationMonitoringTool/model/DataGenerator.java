package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.*;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.EdgeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.FlowJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.NodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.TaskJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter.AdapterTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.TestTask.DELAY_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.TestTask.MESSAGE_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter.AdapterTask.URL_PARAM_ID;

@Component
public class DataGenerator {
    @Autowired
    FlowJPARepository flowJPARepository;

    @Autowired
    NodeJPARepository nodeRepository;

    @Autowired
    EdgeJPARepository edgeRepository;

    @Autowired
    TaskJPARepository taskJPARepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateSimpleFlow() {
        FlowJPA flow = new FlowJPA();
        flow.setId(1L);
        flow.setName("simpleFlow");
        flow = flowJPARepository.save(flow);

        TaskJPA taskJPA = new TaskJPA();
        taskJPA.setId(1L);
        taskJPA.setClassName("by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.TestTask");
        taskJPA.setName("Test Task");
        TaskParameterJPA messageParam = new TaskParameterJPA(
            Long.valueOf(MESSAGE_PARAM_ID), "message", taskJPA
        );
        TaskParameterJPA delayParam = new TaskParameterJPA(
            Long.valueOf(DELAY_PARAM_ID), "delay", taskJPA
        );
        taskJPA.setTaskParameters(Arrays.asList(messageParam, delayParam));
        taskJPA = taskJPARepository.save(taskJPA);

        NodeJPA node1 = new NodeJPA();
        node1.setName("X");
        node1.setFlow(flow);
        node1.setX(100.1);
        node1.setY(100.1);
        node1.setTask(taskJPA);
        node1.setParameters(Arrays.asList(
            new NodeParameterJPA(node1, messageParam, "message of node X"),
            new NodeParameterJPA(node1, delayParam, "3000")
        ));

        NodeJPA node2 = new NodeJPA();
        node2.setName("Y");
        node2.setFlow(flow);
        node2.setX(200.2);
        node2.setY(200.2);
        node2.setTask(taskJPA);
        node2.setParameters(Arrays.asList(
            new NodeParameterJPA(node2, messageParam, "message of node Y"),
            new NodeParameterJPA(node2, delayParam, "1000")
        ));

        node1 = nodeRepository.save(node1);
        node2 = nodeRepository.save(node2);

        EdgeJPA edge1 = new EdgeJPA();
        edge1.setNodeFrom(node1.getId());
        edge1.setNodeTo(node2.getId());
        edgeRepository.save(edge1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateComplexFlow() {
        TaskJPA taskJPA = new TaskJPA();
        taskJPA.setClassName("by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.TestTask");
        taskJPA.setName("Logging Task");
        TaskParameterJPA loggingTaskParameterJPA1 = new TaskParameterJPA(
            Long.valueOf(MESSAGE_PARAM_ID), "message", taskJPA
        );
        TaskParameterJPA loggingTaskParameterJPA2 = new TaskParameterJPA(
            Long.valueOf(DELAY_PARAM_ID), "delay", taskJPA
        );
        taskJPA.setTaskParameters(Arrays.asList(loggingTaskParameterJPA1, loggingTaskParameterJPA2));

        taskJPARepository.save(taskJPA);

        TaskJPA adapterTaskJPA = new TaskJPA();
        adapterTaskJPA.setClassName("by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter.AdapterTask");
        adapterTaskJPA.setName("Adapter Task");
        TaskParameterJPA adapterParamURL = new TaskParameterJPA(
            Long.valueOf(URL_PARAM_ID), "url", adapterTaskJPA
        );
        adapterTaskJPA.setTaskParameters(Collections.singletonList(adapterParamURL));
        taskJPARepository.save(adapterTaskJPA);

        TaskJPA manualTask = new TaskJPA();
        manualTask.setClassName("by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.ManualTask");
        manualTask.setName("Manual Task");
        taskJPARepository.save(manualTask);

        FlowJPA flow = new FlowJPA();
        flow.setId(7L);
        flow.setName("complexFlow");
        flow = flowJPARepository.save(flow);

        NodeJPA nodeA = new NodeJPA();
        nodeA.setName("A");
        nodeA.setFlow(flow);
        nodeA.setTask(manualTask);

        NodeJPA nodeB = new NodeJPA();
        nodeB.setName("B");
        nodeB.setFlow(flow);
        nodeB.setTask(manualTask);

        NodeJPA nodeC = new NodeJPA();
        nodeC.setName("C");
        nodeC.setFlow(flow);
        nodeC.setTask(taskJPA);

        NodeJPA nodeD = new NodeJPA();
        nodeD.setName("D");
        nodeD.setFlow(flow);
        nodeD.setTask(taskJPA);

        NodeJPA nodeE = new NodeJPA();
        nodeE.setName("E");
        nodeE.setFlow(flow);
        nodeE.setTask(taskJPA);

        NodeJPA nodeF = new NodeJPA();
        nodeF.setName("F");
        nodeF.setFlow(flow);
        nodeF.setTask(taskJPA);

        NodeJPA nodeG = new NodeJPA();
        nodeG.setName("G");
        nodeG.setFlow(flow);
        nodeG.setTask(taskJPA);

        NodeJPA nodeI = new NodeJPA();
        nodeI.setName("I");
        nodeI.setFlow(flow);
        nodeI.setTask(taskJPA);

        nodeRepository.saveAll(Arrays.asList(
            nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeI
        ));

        EdgeJPA e1 = new EdgeJPA();
        e1.setNodeFrom(nodeA.getId());
        e1.setNodeTo(nodeC.getId());

        EdgeJPA e2 = new EdgeJPA();
        e2.setNodeFrom(nodeA.getId());
        e2.setNodeTo(nodeD.getId());

        EdgeJPA e3 = new EdgeJPA();
        e3.setNodeFrom(nodeB.getId());
        e3.setNodeTo(nodeD.getId());

        EdgeJPA e4 = new EdgeJPA();
        e4.setNodeFrom(nodeB.getId());
        e4.setNodeTo(nodeG.getId());

        EdgeJPA e5 = new EdgeJPA();
        e5.setNodeFrom(nodeC.getId());
        e5.setNodeTo(nodeF.getId());

        EdgeJPA e6 = new EdgeJPA();
        e6.setNodeFrom(nodeC.getId());
        e6.setNodeTo(nodeE.getId());

        EdgeJPA e7 = new EdgeJPA();
        e7.setNodeFrom(nodeD.getId());
        e7.setNodeTo(nodeE.getId());

        EdgeJPA e8 = new EdgeJPA();
        e8.setNodeFrom(nodeE.getId());
        e8.setNodeTo(nodeI.getId());

        edgeRepository.saveAll(Arrays.asList(
            e1, e2, e3, e4, e5, e6, e7, e8
        ));

    }
}
