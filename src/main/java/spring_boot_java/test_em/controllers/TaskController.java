package spring_boot_java.test_em.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.models.Task;
import spring_boot_java.test_em.services.TaskService;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        log.info("createTask");
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable("id") int id) {
        log.info("findById");
        Task task = taskService.findById(id);
        return task != null ? new ResponseEntity<>(task, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") int id) {
        log.info("deleteById");
        taskService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Task> editTask(@PathVariable("id") int id, @RequestBody Task task) { //todo таск заменить на какаой-то дто
        log.info("editTask");
        Task editedTask = taskService.editTask(id, task);
        return new ResponseEntity<>(editedTask, HttpStatus.OK);
    }

    @GetMapping("/author/{id}")
    public List<Task> findAllTaskByAuthorId(@PathVariable("id") int id) {
        log.info("findAllByAuthorId");
        return taskService.findAllByAuthorId(id);
    }

    @GetMapping("/assignee/{id}")
    public List<Task> findAllTaskByAssigneeId(@PathVariable("id") int id) {
        log.info("findAllByAssigneeId");
        return taskService.findAllByAssigneeId(id);
    }
}
