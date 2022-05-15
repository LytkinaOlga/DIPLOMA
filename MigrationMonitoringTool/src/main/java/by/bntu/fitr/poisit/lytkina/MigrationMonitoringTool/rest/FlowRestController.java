package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FlowPreview;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class FlowRestController {
    @Autowired
    FlowRepository flowRepository;

    @GetMapping("/flows")
    public ResponseEntity<List<FlowPreview>> getFlows() {
        List<Flow> allFlows = flowRepository.findAll();
        List<FlowPreview> allFlowPreviews = allFlows.stream()
            .map(FlowPreview::new)
            .collect(Collectors.toList());
        return new ResponseEntity<>(allFlowPreviews, HttpStatus.OK);
    }

    @GetMapping("/flow/{id}")
    public ResponseEntity<Flow> getFlow(@PathVariable("id") long flowId) {
        Optional<Flow> flow = flowRepository.findById(flowId);
        if (flow.isPresent()) {
            return new ResponseEntity<>(flow.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/flow")
    public ResponseEntity<Long> getFlow(@RequestBody Flow flow) {
        flow = flowRepository.save(flow);
        return new ResponseEntity<>(flow.getId(), HttpStatus.OK);
    }

    @PostMapping("/flows/delete")
    public ResponseEntity<Long> getFlow() {
        flowRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
