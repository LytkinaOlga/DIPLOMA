package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.DataGenerator;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphBuilder;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.FlowJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

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
		dataGenerator.generateMLAdapterFlow();
//		dataGenerator.printSourceInitializationDML(
//			"source.dml.sql",
//			53,
//			284,
//			512
//		);
//		faker.book().title();
//		dataGenerator.generateTwoTestTasksFlow();
//		dataGenerator.generateSingleTaskFlow(Constants.Tasks.ManualTask.ID);
//		dataGenerator.generateSingleTaskFlow(Constants.Tasks.MasterListCreator.ID,
//			new NodeParameterJPA(Constants.Tasks.MasterListCreator.ENTITY_TABLE_PARAM_ID, "migr_test"),
//			new NodeParameterJPA(Constants.Tasks.MasterListCreator.ENTITY_COLUMN_PARAM_ID, "id")
//		);
	}
}
