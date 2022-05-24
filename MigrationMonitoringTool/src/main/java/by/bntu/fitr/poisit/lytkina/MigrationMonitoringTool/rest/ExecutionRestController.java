package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionGraph;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphBuilder;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/execution")
public class ExecutionRestController {
    @Autowired
    FlowRepository flowRepository;
    @Autowired
    ExecutionJPARepository executionJPARepository;
    @Autowired
    GraphBuilder graphBuilder;

    @GetMapping("/{executionId}")
    public ResponseEntity<ExecutionDTO> getExecutionStatus(@PathVariable Long executionId) {
        ExecutionJPA executionJPA = executionJPARepository.getById(executionId);
        return ResponseEntity.ok(new ExecutionDTO(executionJPA));
    }

    @PostMapping("{flowId}/start")
    public ResponseEntity<?> startFlow(@PathVariable Long flowId) {
        ExecutionGraph executionGraph = graphBuilder.buildGraph(flowId);
        ExecutionJPA executionJPA = executionGraph.run();
        return ResponseEntity.ok(executionJPA.getId());
    }

    @PostMapping("{executionId}/complete/{nodeId}")
    public ResponseEntity<?> forceCompleteNode(@PathVariable Long executionId,
                                                    @PathVariable Long nodeId) {
        ExecutionGraph executionGraph = ExecutionGraph.getByExecutionId(executionId);
        executionGraph.forceCompleteNode(nodeId);

        return ResponseEntity.ok().build();
    }
}
