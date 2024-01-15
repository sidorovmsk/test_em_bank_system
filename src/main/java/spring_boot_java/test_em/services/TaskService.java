package spring_boot_java.test_em.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.exceptions.ResourceNotFoundException;
import spring_boot_java.test_em.models.Task;
import spring_boot_java.test_em.repositories.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task findById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }

    public Task editTask(int id, Task task) {
        Task taskFromDB = findById(id);
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (taskFromDB.getAuthor().getUsername().equals(authUserName))
            editIfAuthor(taskFromDB, task);
        else if (taskFromDB.getAssignee().getUsername().equals(authUserName))
            editIfAssignee(taskFromDB, task);
        return taskRepository.save(taskFromDB);
    }

    private void editIfAuthor(Task db, Task task) {
        // Используем BeanUtils.copyProperties для копирования свойств, игнорируя null значения
        BeanUtils.copyProperties(task, db, "id");
    }

    private void editIfAssignee(Task taskFromDB, Task task) {
        taskFromDB.setStatus(task.getStatus());
    }

}
