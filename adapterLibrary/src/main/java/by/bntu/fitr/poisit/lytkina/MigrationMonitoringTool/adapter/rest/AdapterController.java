package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.Adapter;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.AdapterResponse;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.AdapterStatus;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.config.DBConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AdapterController {

    @Autowired
    @Lazy
    DBConfiguration dbConfiguration;

    @Autowired
    Adapter adapter;

    @GetMapping("/status")
    public ResponseEntity<AdapterStatus> getStatus() {
        return ResponseEntity.ok(adapter.getStatus());
    }

    @PostMapping("/start")
    public ResponseEntity<AdapterResponse> start(@RequestBody StartAdapterDTO dto) {
        return ResponseEntity.ok(adapter.start(dto.getMasterListTable()));
    }

    @PostMapping("/stop")
    public ResponseEntity<AdapterResponse> stop() {
        return ResponseEntity.ok(adapter.stop());
    }

    @PostMapping("/configure")
    public ResponseEntity<?> stop(@RequestBody ConfigureAdapterDTO configureAdapterDTO) {
        dbConfiguration.update(configureAdapterDTO);
        return ResponseEntity.ok().build();
    }
}
