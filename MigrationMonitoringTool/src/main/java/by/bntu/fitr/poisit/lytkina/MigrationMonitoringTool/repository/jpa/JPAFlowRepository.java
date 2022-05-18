package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAFlowRepository extends JpaRepository<FlowJPA, Long> {
}
