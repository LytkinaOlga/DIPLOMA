package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class MigrationMonitoringToolApplication {
	@Autowired
	FlowRepository flowRepository;

	public static void main(String[] args) {
		SpringApplication.run(MigrationMonitoringToolApplication.class, args);

	}

	public void run(String... args) throws Exception {
		Flow flow = new Flow();
		flow.setName("michFlow");
		flowRepository.save(flow);
	}
}
