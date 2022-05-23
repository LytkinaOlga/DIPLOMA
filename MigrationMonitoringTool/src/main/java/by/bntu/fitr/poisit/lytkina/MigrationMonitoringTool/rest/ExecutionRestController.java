package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionGraph;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphBuilder;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution")
public class ExecutionRestController {
    @Autowired
    FlowRepository flowRepository;
    @Autowired
    GraphBuilder graphBuilder;

    @PostMapping("{flowId}/start")
    public ResponseEntity<?> startFlow(@PathVariable Long flowId) {
        ExecutionGraph executionGraph = graphBuilder.buildGraph(flowId);
        executionGraph.run();

        return ResponseEntity.ok().build();
    }

    @PostMapping("{executionId}/complete/{nodeId}")
    public ResponseEntity<?> forceCompleteNode(@PathVariable Long executionId,
                                                    @PathVariable Long nodeId) {
        ExecutionGraph executionGraph = ExecutionGraph.getByExecutionId(executionId);
        executionGraph.forceCompleteNode(nodeId);

        return ResponseEntity.ok().build();
    }
}
