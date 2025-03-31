package com.myApp.task_management.Controller;

import com.myApp.task_management.Entity.TaskDTO;
import com.myApp.task_management.Repository.TaskManagementRespository;
import com.myApp.task_management.Service.TaskManagementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/v1/task-management")
public class TaskManagementController {

    private final TaskManagementRespository taskManagementRespository;
    private final TaskManagementService taskManagementService;

    TaskManagementController(TaskManagementRespository taskManagementRespository, TaskManagementService taskManagementService) {
        this.taskManagementRespository = taskManagementRespository;
        this.taskManagementService = taskManagementService;
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO taskDTO = taskManagementService.getTaskById(id);
        log.info("Sending the reponse back ! {}", taskDTO.toString());
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @GetMapping("/task")
    public ResponseEntity<List<TaskDTO>> getTasks() {
        List<TaskDTO> tasks = taskManagementService.getTasks();
        log.info("Sending the reponse back !");
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        TaskDTO newTaskDTO = taskManagementService.createTask(taskDTO);
        log.info("Sending the reponse back ! {}", newTaskDTO.toString());
        return new ResponseEntity<>(newTaskDTO, HttpStatus.CREATED);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody TaskDTO updatedTaskDTO) {
        TaskDTO newTaskDTO = taskManagementService.updateTask(id, updatedTaskDTO);
        log.info("Sending the reponse back ! {}", newTaskDTO.toString());
        return new ResponseEntity<>(newTaskDTO, HttpStatus.PARTIAL_CONTENT);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {

        taskManagementService.deleteTask(id);
        log.info("Sending the response back !");
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);


    }

    @PatchMapping("task/{id}")
    public ResponseEntity<Object> updatePartialTask(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            TaskDTO updatedTaskDTO = taskManagementService.partialUpdateTask(id, updates);
            log.info("Sending the reponse back ! {}", updatedTaskDTO.toString());
            return new ResponseEntity<>(updatedTaskDTO, HttpStatus.PARTIAL_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
