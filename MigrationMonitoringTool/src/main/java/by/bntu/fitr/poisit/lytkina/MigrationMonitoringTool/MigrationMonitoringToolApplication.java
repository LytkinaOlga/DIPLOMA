package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.JPAEdgeRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.JPAFlowRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.JPANodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;

@SpringBootApplication
public class MigrationMonitoringToolApplication implements CommandLineRunner {
	@Autowired
	JPAFlowRepository jpaFlowRepository;

	@Autowired
	JPANodeRepository nodeRepository;

	@Autowired
	JPAEdgeRepository edgeRepository;

	@Autowired
	FlowRepository flowRepository;

	public static void main(String[] args) {
		SpringApplication.run(MigrationMonitoringToolApplication.class, args);

	}

	public void run(String... args) throws Exception {
		generateData();
		Collection<Flow> flows = flowRepository.findAll();
		System.out.println("ok");
	}

	private void generateData() {
		FlowJPA flow = new FlowJPA();
		flow.setName("flow1");
		flow = jpaFlowRepository.save(flow);

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
		edge1.setNodeFrom(node1);
		edge1.setNodeTo(node2);
		edge1 = edgeRepository.save(edge1);
	}
}
