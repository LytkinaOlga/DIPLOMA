package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionJPARepository extends JpaRepository<ExecutionJPA, Long> {
    List<ExecutionJPA> findExecutionJPAByFlowJPAId(Long flowId);
}
