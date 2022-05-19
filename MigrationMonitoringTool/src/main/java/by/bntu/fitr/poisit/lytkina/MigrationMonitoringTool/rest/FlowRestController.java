package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FlowPreviewDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FullFlowDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.JPAFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class FlowRestController {
    @Autowired
    JPAFlowRepository jpaFlowRepository;

    @Autowired
    FlowRepository flowRepository;

    @GetMapping("/flows")
    public ResponseEntity<List<FlowPreviewDTO>> getFlows() {
        List<FlowJPA> allFlows = jpaFlowRepository.findAll();
        List<FlowPreviewDTO> allFlowPreviewDTOS = allFlows.stream()
            .map(FlowPreviewDTO::new)
            .collect(Collectors.toList());
        return new ResponseEntity<>(allFlowPreviewDTOS, HttpStatus.OK);
    }

    @GetMapping("/flows2")
    public ResponseEntity<List<FullFlowDTO>> getFullFlows() {
        Collection<Flow> allFlows = flowRepository.findAll();
        List<FullFlowDTO> allFlowDTOs = allFlows.stream()
            .map(FullFlowDTO::new)
            .collect(Collectors.toList());
        return new ResponseEntity<>(allFlowDTOs, HttpStatus.OK);
    }

    @GetMapping("/flow/{id}")
    public ResponseEntity<Flow> getFullFlow(@PathVariable("id") long flowId) {
        Optional<Flow> flow = flowRepository.findById(flowId);
        return flow
            .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/flow")
    public ResponseEntity<Long> getFlow(@RequestBody FlowJPA flow) {
        flow = jpaFlowRepository.save(flow);
        return new ResponseEntity<>(flow.getId(), HttpStatus.OK);
    }

    @PostMapping("/flows/delete")
    public ResponseEntity<Long> getFlow() {
        jpaFlowRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}