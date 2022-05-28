package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FlowPreviewDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FlowDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.Flow;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.FlowRepository;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.FlowJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FlowRestController {
    @Autowired
    FlowJPARepository flowJPARepository;

    @Autowired
    FlowRepository flowRepository;

    @GetMapping("/flows")
    public ResponseEntity<List<FlowPreviewDTO>> getFlows() {
        List<FlowJPA> allFlows = flowJPARepository.findAll();
        List<FlowPreviewDTO> allFlowPreviewDTOS = allFlows.stream()
            .map(FlowPreviewDTO::new)
            .collect(Collectors.toList());
        return new ResponseEntity<>(allFlowPreviewDTOS, HttpStatus.OK);
    }

    @GetMapping("/flow/{id}")
    public ResponseEntity<FlowDTO> getFullFlow(@PathVariable("id") long flowId) {
        Optional<Flow> flow = flowRepository.findById(flowId);
        return flow
            .map(f -> new ResponseEntity<>(new FlowDTO(f), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/flow")
    public ResponseEntity<Long> saveFullFlow(@Valid @RequestBody FlowDTO flowDTO) {
        Long flowId = flowRepository.updateFlow(flowDTO);
        return new ResponseEntity<>(flowId, HttpStatus.OK);
    }

    @PostMapping("/flow/delete/{id}")
    public ResponseEntity<Long> deleteFlow(@PathVariable("id") long flowId) {
        flowJPARepository.deleteById(flowId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/flow/deleteall")
    public ResponseEntity<Long> getFlow() {
        flowJPARepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
