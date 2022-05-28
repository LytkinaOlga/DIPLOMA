package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.DataGenerator;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphBuilder;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.FlowJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class MigrationMonitoringToolApplication implements CommandLineRunner {
	@Autowired
	DataGenerator dataGenerator;

	@Autowired
	GraphBuilder graphBuilder;


	@Autowired
	FlowJPARepository flowJPARepository;

	public static void main(String[] args) {
		SpringApplication.run(MigrationMonitoringToolApplication.class, args);

	}

	@Transactional
	public void run(String... args) throws Exception {
//		dataGenerator.generateSimpleFlow();
//		dataGenerator.generateSimpleAdapterFlow();
//		Collection<FlowJPA> flowJPAS = flowJPARepository.findAll();
//		ExecutionGraph executionGraph = graphBuilder.buildGraph(flowJPAS.iterator().next().getId());
//		executionGraph.run();
	}
}
