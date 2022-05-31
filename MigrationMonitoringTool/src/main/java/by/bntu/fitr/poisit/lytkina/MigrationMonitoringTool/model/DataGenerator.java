package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.*;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.*;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants;
import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.MasterListAdapter.URL_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.MasterListCreator.ENTITY_COLUMN_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.MasterListCreator.ENTITY_TABLE_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.RandomFailingAdapter.SUCCESS_RATE_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.TestTask.DELAY_PARAM_ID;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.Tasks.TestTask.MESSAGE_PARAM_ID;

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

    @Autowired
    TaskParameterJPARepository taskParameterJPARepository;

    public void printSourceInitializationDML(String fileName, int productsCount, int customersCount, int ordersCount) {
        try {

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        Faker faker = new Faker();

        for (int i = 1; i < productsCount+1; i++) {
            Book book = faker.book();
            bw.append(
                "insert into products(id, name, price, total_quantity) values ("
                + i + ", "
                + "'\"" +book.title().replace("'", "''") + "\" by " + book.author().replace("'", "''") + "', "
                + faker.numerify("###.##") + ", "
                + faker.number().numberBetween(1, 100)
                + ");\n"
            );
        }
        for (int i = 1; i < customersCount+1; i++) {
            Name name = faker.name();
            bw.append(
                "insert into customers(id, first_name, last_name) values ("
                    + i + ", "
                    + "'" +name.firstName().replace("'", "''") + "', "
                    + "'" + name.lastName().replace("'", "''") + "'"
                    + ");\n"
            );
        }
        for (int i = 1; i < ordersCount+1; i++) {
            Date submitDate = faker.date().past(365, TimeUnit.DAYS);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:m:ss");
            int customerId = new Random().nextInt(customersCount-1) + 1;
            bw.append(
                "insert into orders(id, customer_id, submit_date) values ("
                    + i + ", "
                    + customerId + ", "
                    + "TO_TIMESTAMP('" + sdf.format(submitDate) + "', 'YYYY-MM-DD HH24:MI:SS')"
                    + ");\n"
            );
        }
        for (int i = 1; i < ordersCount; i++) {
            Random random = new Random();
            int productsInOrder = (int) (Math.floor(-1 * Math.log(1-random.nextDouble())) % productsCount) + 1;
            HashSet<Integer> usedProducts = new HashSet<>();
            for (int productNumber = 0; productNumber < productsInOrder; productNumber++) {
                int productId = new Random().nextInt(productsCount-1) + 1;
                while (usedProducts.contains(productId)) {
                    productId = new Random().nextInt(productsCount-1) + 1;
                }
                usedProducts.add(productId);
                int productQuantityInOrder = new Random().nextInt(15) + 1;

                bw.append(
                    "insert into orders_products(order_id, product_id, quantity) values ("
                        + i + ", "
                        + productId + ", "
                        + productQuantityInOrder
                        + ");\n"
                );
            }
        }
        bw.flush();
        bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateTwoTestTasksFlow() {
        FlowJPA flow = new FlowJPA();
        flow.setName("simpleFlow");
        flow = flowJPARepository.save(flow);

        TaskJPA testTaskJPA = taskJPARepository.getById(Constants.Tasks.TestTask.ID);
        TaskParameterJPA messageParam = taskParameterJPARepository.getById(MESSAGE_PARAM_ID);
        TaskParameterJPA delayParam = taskParameterJPARepository.getById(DELAY_PARAM_ID);

        NodeJPA node1 = new NodeJPA();
        node1.setName("X");
        node1.setFlow(flow);
        node1.setX(100.1);
        node1.setY(100.1);
        node1.setTask(testTaskJPA);
        node1.setParameters(Arrays.asList(
            new NodeParameterJPA(node1, messageParam, "message of node X"),
            new NodeParameterJPA(node1, delayParam, "3000")
        ));

        NodeJPA node2 = new NodeJPA();
        node2.setName("Y");
        node2.setFlow(flow);
        node2.setX(200.2);
        node2.setY(200.2);
        node2.setTask(testTaskJPA);
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

    @Transactional
    public void generateSingleTaskFlow(Long taskId, NodeParameterJPA ... params) {
        FlowJPA flow = new FlowJPA();
        flow.setName("singleTaskFlow");
        flow = flowJPARepository.save(flow);

        NodeJPA node1 = new NodeJPA();
        node1.setName("single task");
        node1.setFlow(flow);
        node1.setTask(taskJPARepository.getById(taskId));

        List<NodeParameterJPA> nodeParams = new ArrayList<>();
        for (NodeParameterJPA param : params) {
            TaskParameterJPA taskParam = taskParameterJPARepository.getById(param.getParameter().getId());
            NodeParameterJPA nodeParam = new NodeParameterJPA(node1, taskParam, param.getValue());
            nodeParams.add(nodeParam);
        }

        node1.setParameters(nodeParams);

        nodeRepository.save(node1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateMLFakeAdapterFlow() {
        FlowJPA flow = new FlowJPA();
        flow.setId(1L);
        flow.setName("simpleFlow");
        flow = flowJPARepository.save(flow);

        TaskJPA masterListTask = taskJPARepository.getById(Constants.Tasks.MasterListCreator.ID);
        TaskParameterJPA entityTableParam = taskParameterJPARepository.getById(ENTITY_TABLE_PARAM_ID);
        TaskParameterJPA entityIdColumnParam = taskParameterJPARepository.getById(ENTITY_COLUMN_PARAM_ID);

        TaskJPA randomFailingAdapterTask = taskJPARepository.getById(Constants.Tasks.RandomFailingAdapter.ID);
        TaskParameterJPA successRateParam = taskParameterJPARepository.getById(SUCCESS_RATE_PARAM_ID);

        NodeJPA node1 = new NodeJPA();
        node1.setName("Master List Creation");
        node1.setFlow(flow);
        node1.setX(100.1);
        node1.setY(100.1);
        node1.setTask(masterListTask);
        node1.setParameters(Arrays.asList(
            new NodeParameterJPA(node1, entityTableParam, "customers"),
            new NodeParameterJPA(node1, entityIdColumnParam, "id")
        ));

        NodeJPA node2 = new NodeJPA();
        node2.setName("Random Failing Adapter 0.2");
        node2.setFlow(flow);
        node2.setX(200.2);
        node2.setY(200.2);
        node2.setTask(randomFailingAdapterTask);
        node2.setParameters(Arrays.asList(
            new NodeParameterJPA(node2, successRateParam, "0.5")
        ));

        NodeJPA node3 = new NodeJPA();
        node3.setName("Random Failing Adapter 0.6");
        node3.setFlow(flow);
        node3.setX(200.2);
        node3.setY(200.2);
        node3.setTask(randomFailingAdapterTask);
        node3.setParameters(Arrays.asList(
            new NodeParameterJPA(node3, successRateParam, "0.5")
        ));

        node1 = nodeRepository.save(node1);
        node2 = nodeRepository.save(node2);
        node3 = nodeRepository.save(node3);

        EdgeJPA edge1 = new EdgeJPA();
        edge1.setNodeFrom(node1.getId());
        edge1.setNodeTo(node2.getId());

        EdgeJPA edge2 = new EdgeJPA();
        edge2.setNodeFrom(node1.getId());
        edge2.setNodeTo(node3.getId());

        edgeRepository.save(edge1);
        edgeRepository.save(edge2);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateMLAdapterFlow() {
        FlowJPA flow = new FlowJPA();
        flow.setId(1L);
        flow.setName("Customer Migration");
        flow = flowJPARepository.save(flow);

        TaskJPA masterListTask = taskJPARepository.getById(Constants.Tasks.MasterListCreator.ID);
        TaskParameterJPA entityTableParam = taskParameterJPARepository.getById(ENTITY_TABLE_PARAM_ID);
        TaskParameterJPA entityIdColumnParam = taskParameterJPARepository.getById(ENTITY_COLUMN_PARAM_ID);

        TaskJPA randomFailingAdapterTask = taskJPARepository.getById(Constants.Tasks.MasterListAdapter.ID);
        TaskParameterJPA urlParam = taskParameterJPARepository.getById(URL_PARAM_ID);

        NodeJPA node1 = new NodeJPA();
        node1.setName("Master List Creation");
        node1.setFlow(flow);
        node1.setX(100.1);
        node1.setY(100.1);
        node1.setTask(masterListTask);
        node1.setParameters(Arrays.asList(
            new NodeParameterJPA(node1, entityTableParam, "customers"),
            new NodeParameterJPA(node1, entityIdColumnParam, "id")
        ));

        NodeJPA node2 = new NodeJPA();
        node2.setName("Validation Adapter");
        node2.setFlow(flow);
        node2.setX(200.2);
        node2.setY(200.2);
        node2.setTask(randomFailingAdapterTask);
        node2.setParameters(Arrays.asList(
            new NodeParameterJPA(node2, urlParam, "http://localhost:8091")
        ));

        NodeJPA node3 = new NodeJPA();
        node3.setName("Customer Migration Adapter");
        node3.setFlow(flow);
        node3.setX(200.2);
        node3.setY(300.2);
        node3.setTask(randomFailingAdapterTask);
        node3.setParameters(Arrays.asList(
            new NodeParameterJPA(node3, urlParam, "http://localhost:8092")
        ));

        node1 = nodeRepository.save(node1);
        node2 = nodeRepository.save(node2);
        node3 = nodeRepository.save(node3);

        EdgeJPA edge1 = new EdgeJPA();
        edge1.setNodeFrom(node1.getId());
        edge1.setNodeTo(node2.getId());

        EdgeJPA edge2 = new EdgeJPA();
        edge2.setNodeFrom(node2.getId());
        edge2.setNodeTo(node3.getId());

        edgeRepository.save(edge1);
        edgeRepository.save(edge2);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateComplexFlow() {
        TaskJPA testTaskJPA = taskJPARepository.getById(Constants.Tasks.TestTask.ID);
        TaskParameterJPA messageParameterJPA = taskParameterJPARepository.getById(MESSAGE_PARAM_ID);
        TaskParameterJPA delayParameterJPA = taskParameterJPARepository.getById(DELAY_PARAM_ID);

        TaskJPA adapterTaskJPA = taskJPARepository.getById(Constants.Tasks.MasterListAdapter.ID);
        TaskParameterJPA adapterParamURL = taskParameterJPARepository.getById(URL_PARAM_ID);

        TaskJPA manualTask = taskJPARepository.getById(Constants.Tasks.ManualTask.ID);

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
        nodeC.setTask(testTaskJPA);

        NodeJPA nodeD = new NodeJPA();
        nodeD.setName("D");
        nodeD.setFlow(flow);
        nodeD.setTask(testTaskJPA);

        NodeJPA nodeE = new NodeJPA();
        nodeE.setName("E");
        nodeE.setFlow(flow);
        nodeE.setTask(testTaskJPA);

        NodeJPA nodeF = new NodeJPA();
        nodeF.setName("F");
        nodeF.setFlow(flow);
        nodeF.setTask(testTaskJPA);

        NodeJPA nodeG = new NodeJPA();
        nodeG.setName("G");
        nodeG.setFlow(flow);
        nodeG.setTask(testTaskJPA);

        NodeJPA nodeI = new NodeJPA();
        nodeI.setName("I");
        nodeI.setFlow(flow);
        nodeI.setTask(testTaskJPA);

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
