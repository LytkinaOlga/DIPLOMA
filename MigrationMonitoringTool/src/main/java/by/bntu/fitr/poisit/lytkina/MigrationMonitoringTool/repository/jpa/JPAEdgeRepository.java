package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface JPAEdgeRepository extends CrudRepository<EdgeJPA, Long> {
    Collection<EdgeJPA> findAllByNodeFromId(Long nodeFromId);
    Collection<EdgeJPA> deleteAllByNodeFromId(Long nodeFromId);
}
