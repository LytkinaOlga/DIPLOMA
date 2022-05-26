package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.execution.ExecutionPreviewDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionGraph;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphBuilder;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Execution;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.ExecutionJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.ExecutionRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.ExecutionJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.CollectionHelper.mapCollect;

@RestController
@RequestMapping
public class ExecutionRestController {
    @Autowired
    FlowRepository flowRepository;
    @Autowired
    ExecutionRepository executionRepository;
    @Autowired
    ExecutionJPARepository executionJPARepository;
    @Autowired
    GraphBuilder graphBuilder;

    @GetMapping("/executions")
    public ResponseEntity<List<ExecutionPreviewDTO>> getAllExecutions() {
        List<ExecutionJPA> executions = executionJPARepository.findAll();
        List<ExecutionPreviewDTO> executionPreviewDTOS = mapCollect(executions, ExecutionPreviewDTO::new);
        return ResponseEntity.ok(executionPreviewDTOS);
    }


    @GetMapping("/execution")
    public ResponseEntity<List<ExecutionPreviewDTO>> getExecutionsByFlowId(@RequestParam Long flowId) {
        List<ExecutionJPA> executions = executionJPARepository.findExecutionJPAByFlowJPAId(flowId);
        List<ExecutionPreviewDTO> executionPreviewDTOS = mapCollect(executions, ExecutionPreviewDTO::new);
        return ResponseEntity.ok(executionPreviewDTOS);
    }

    @GetMapping("/execution/{executionId}")
    public ResponseEntity<ExecutionDTO> getExecution(@PathVariable Long executionId) {
        Optional<Execution> execution = executionRepository.findById(executionId);
        return execution
            .map(e -> new ResponseEntity<>(new ExecutionDTO(e), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/execution/start")
    public ResponseEntity<?> startFlow(@RequestParam Long flowId) {
        ExecutionGraph executionGraph = graphBuilder.buildGraph(flowId);
        Execution execution = executionGraph.run();
        return ResponseEntity.ok(execution.getId());
    }

    @PostMapping("/execution/{executionId}/complete/{nodeId}")
    public ResponseEntity<?> forceCompleteNode(@PathVariable Long executionId,
                                                    @PathVariable Long nodeId) {
        ExecutionGraph executionGraph = ExecutionGraph.getByExecutionId(executionId);
        executionGraph.forceCompleteNode(nodeId);

        return ResponseEntity.ok().build();
    }
}
