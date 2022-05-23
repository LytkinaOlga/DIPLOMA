package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.TaskJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TaskJPARepository extends JpaRepository<TaskJPA, Long> {
}
