package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.FlowPreviewDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto.TaskDTO;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.FlowJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.TaskJPA;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.repository.jpa.TaskJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskRestController {
    @Autowired
    TaskJPARepository taskJPARepository;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks() {
        Collection<TaskJPA> allTasks = taskJPARepository.findAll();
        List<TaskDTO> allTasksDTO = allTasks.stream()
            .map(TaskDTO::new)
            .collect(Collectors.toList());
        return new ResponseEntity<>(allTasksDTO, HttpStatus.OK);
    }

}
