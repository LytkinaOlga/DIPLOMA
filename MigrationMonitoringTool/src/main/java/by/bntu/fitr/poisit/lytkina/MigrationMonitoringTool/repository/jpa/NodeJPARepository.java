package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.NodeJPA;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface NodeJPARepository extends JpaRepository<NodeJPA, Long> {
    Collection<NodeJPA> findAllByFlowId(Long flowId);

    @EntityGraph(attributePaths = {"outgoingEdges", "incomingEdges", "task"})
    Collection<NodeJPA> findAllDeepByFlowId(Long flowId);
}
