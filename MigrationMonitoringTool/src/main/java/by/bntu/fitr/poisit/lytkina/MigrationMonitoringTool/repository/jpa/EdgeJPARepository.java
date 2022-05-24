package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface EdgeJPARepository extends CrudRepository<EdgeJPA, Long> {
    Collection<EdgeJPA> findAllByNodeFrom(Long nodeFromId);
    Collection<EdgeJPA> deleteAllByNodeFrom(Long nodeFromId);

//    @Query(
////        "select EdgeJPA from EdgeJPA join fetch NodeJPA on NodeJPA = EdgeJPA.nodeFrom join fetch FlowJPA on FlowJPA = NodeJPA.flow where FlowJPA .id=?1"
//        "select e from EdgeJPA e join fetch e.nodeFrom n join fetch n.flow f where f.id=?1"
//    )
//    Collection<EdgeJPA> findDeepEdgeById(Long flowId);
}
