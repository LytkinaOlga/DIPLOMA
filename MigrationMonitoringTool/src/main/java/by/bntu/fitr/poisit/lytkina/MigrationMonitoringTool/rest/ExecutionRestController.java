package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionGraph;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphBuilder;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Execution;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ExecutionRepository;
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
    ExecutionRepository executionRepository;
    @Autowired
    GraphBuilder graphBuilder;

    @GetMapping("/{executionId}")
    public ResponseEntity<ExecutionDTO> getExecution(@PathVariable Long executionId) {
        Execution execution = executionRepository.findById(executionId);
        return ResponseEntity.ok(new ExecutionDTO(execution));
    }

    @PostMapping("{flowId}/start")
    public ResponseEntity<?> startFlow(@PathVariable Long flowId) {
        ExecutionGraph executionGraph = graphBuilder.buildGraph(flowId);
        Execution execution = executionGraph.run();
        return ResponseEntity.ok(execution.getId());
    }

    @PostMapping("{executionId}/complete/{nodeId}")
    public ResponseEntity<?> forceCompleteNode(@PathVariable Long executionId,
                                                    @PathVariable Long nodeId) {
        ExecutionGraph executionGraph = ExecutionGraph.getByExecutionId(executionId);
        executionGraph.forceCompleteNode(nodeId);

        return ResponseEntity.ok().build();
    }
}
