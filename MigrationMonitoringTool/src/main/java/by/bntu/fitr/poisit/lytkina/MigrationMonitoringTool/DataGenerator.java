package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.*;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.EdgeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.FlowJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.NodeJPARepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.TaskJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

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
    public void generateData() {
        FlowJPA flow = new FlowJPA();
        flow.setName("flow1");
        flow = flowJPARepository.save(flow);

        NodeJPA node1 = new NodeJPA();
        node1.setName("node1");
        node1.setFlow(flow);
        node1.setX(100.1);
        node1.setY(100.1);

        NodeJPA node2 = new NodeJPA();
        node2.setName("node2");
        node2.setFlow(flow);
        node2.setX(200.2);
        node2.setY(200.2);

        node1 = nodeRepository.save(node1);
        node2 = nodeRepository.save(node2);

        EdgeJPA edge1 = new EdgeJPA();
        edge1.setNodeFrom(node1.getId());
        edge1.setNodeTo(node2.getId());
        edge1 = edgeRepository.save(edge1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateComplexData() {
        TaskJPA taskJPA = new TaskJPA();
        taskJPA.setClassName("by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.TestTask");
        taskJPA.setName("Logging Task");
        TaskParameterJPA loggingTaskParameterJPA1 = new TaskParameterJPA(taskJPA, "message", "log message");
        TaskParameterJPA loggingTaskParameterJPA2 = new TaskParameterJPA(taskJPA, "delay", "3000");
        taskJPA.setTaskParameters(Arrays.asList(loggingTaskParameterJPA1, loggingTaskParameterJPA2));

        taskJPARepository.save(taskJPA);

        TaskJPA adapterTaskJPA = new TaskJPA();
        adapterTaskJPA.setClassName("by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter.AdapterTask");
        adapterTaskJPA.setName("Adapter Task");
        TaskParameterJPA adapterParamURL = new TaskParameterJPA(adapterTaskJPA, "url", "http://localhost:8081");
        adapterTaskJPA.setTaskParameters(Collections.singletonList(adapterParamURL));
        taskJPARepository.save(adapterTaskJPA);

        TaskJPA manualTask = new TaskJPA();
        manualTask.setClassName("by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.ManualTask");
        manualTask.setName("Manual Task");
        taskJPARepository.save(manualTask);

        FlowJPA flow = new FlowJPA();
        flow.setId(1l);
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
