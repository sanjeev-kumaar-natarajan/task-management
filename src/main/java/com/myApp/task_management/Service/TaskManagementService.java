package com.myApp.task_management.Service;

import com.myApp.task_management.Entity.Priority;
import com.myApp.task_management.Entity.Task;
import com.myApp.task_management.Entity.TaskDTO;
import com.myApp.task_management.Mapper.TaskMapper;
import com.myApp.task_management.Repository.TaskManagementRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TaskManagementService {

    private final TaskManagementRespository taskManagementRespository;

    private final TaskMapper taskMapper;

    TaskManagementService(TaskManagementRespository taskManagementRespository, TaskMapper taskMapper) {
        this.taskManagementRespository = taskManagementRespository;
        this.taskMapper = taskMapper;
    }

    @Cacheable(value = "Tasks", key = "#id")
    public TaskDTO getTaskById(Long id) {
        Task task = taskManagementRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with given ID " + id + " not found"));
        log.info("Task with the given Id - {} found !", id);
        TaskDTO taskDTO = taskMapper.toTaskDTO(task);
        return taskDTO;
    }

    public List<TaskDTO> getTasks() {
        List<TaskDTO> taskDTOList = taskManagementRespository.getTaskList().stream().map(taskMapper::toTaskDTO).toList();
        log.info("Returning list of tasks");
        return taskDTOList;
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task = taskMapper.toTaskEntity(taskDTO);
        task = taskManagementRespository.save(task);
        log.info("Task created successfully with Id - {}", task.getId());
        TaskDTO newTaskDto = taskMapper.toTaskDTO(task);
        return newTaskDto;
    }

    @CachePut(value = "Tasks", key = "#id")
    public TaskDTO updateTask(Long id, TaskDTO updatedTaskDTO) {
        if (taskManagementRespository.existsById(id)) {
            Task updatedTask = taskMapper.toTaskEntity(updatedTaskDTO);
            updatedTask.setId(id);
            Task newTask = taskManagementRespository.save(updatedTask);
            log.info("Task with Id - {} updated!", id);
            TaskDTO newTaskDTO = taskMapper.toTaskDTO(newTask);
            return newTaskDTO;
        } else {
            throw new ResourceNotFoundException("Task with the given Id " + id + " not found");
        }
    }

    @CacheEvict(value = "Tasks", key = "#id")
    public void deleteTask(Long id) {

        if (taskManagementRespository.existsById(id)) {
            taskManagementRespository.deleteById(id);
            log.info("Task with Id - {} deleted", id);
        } else {
            throw new ResourceNotFoundException("Task with the given Id " + id + " not found");
        }

    }

    public TaskDTO partialUpdateTask(Long id, Map<String, Object> updates) throws Exception {
        Task existingTask = taskManagementRespository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Task with given Id" + id + "not found"));
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Task.class, key);
            if (field != null) {
                field.setAccessible(true);
                if (key.equals("priority") && value instanceof String) {
                    Priority priority = Priority.valueOf((String) value);
                    ReflectionUtils.setField(field, existingTask, priority);
                } else if (value != null)
                    ReflectionUtils.setField(field, existingTask, value);
            } else {
                throw new IllegalArgumentException("Undefined field");
            }
        });
        Task updatedtask = taskManagementRespository.save(existingTask);
        log.info("Task with Id - {} updated!", id);
        TaskDTO updatedTaskDTO = taskMapper.toTaskDTO(updatedtask);
        return updatedTaskDTO;
    }

}
