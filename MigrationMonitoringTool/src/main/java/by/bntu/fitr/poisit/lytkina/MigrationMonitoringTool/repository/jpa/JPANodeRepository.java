package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface JPANodeRepository extends CrudRepository<NodeJPA, Long> {
    Collection<NodeJPA> findAllByFlowId(Long flowId);
}
