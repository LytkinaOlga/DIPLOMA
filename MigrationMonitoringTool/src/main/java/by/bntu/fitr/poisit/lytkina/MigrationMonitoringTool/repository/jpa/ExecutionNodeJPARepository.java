package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionNodeJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionProgressJPAPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionNodeJPARepository extends JpaRepository<ExecutionNodeJPA, ExecutionProgressJPAPK> {
    List<ExecutionNodeJPA> findAllByExecutionId(Long executionId);
}
