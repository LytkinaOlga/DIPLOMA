package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface NodeJPARepository extends CrudRepository<NodeJPA, Long> {
    Collection<NodeJPA> findAllByFlowId(Long flowId);

    @EntityGraph(attributePaths = {"outgoingEdges", "incomingEdges"})
    Collection<NodeJPA> findAllDeepByFlowId(Long flowId);
}
