package pl.zzpwjj.restaurant.core.taskexample.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.taskexample.model.inputs.AddTaskInput;
import pl.zzpwjj.restaurant.core.taskexample.model.dto.TaskDto;
import pl.zzpwjj.restaurant.core.taskexample.model.entities.Task;
import pl.zzpwjj.restaurant.core.taskexample.repositories.TasksRepository;

@Service
public class TasksService {

    private TasksRepository tasksRepository;

    @Autowired
    public TasksService(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> getTasks() {
        return tasksRepository.findAll();
    }

    public Task getTask(final Long id) throws ItemNotFoundException {
        return tasksRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Task getnada() throws ItemNotFoundException {
        Task lol = new Task();
        lol.setTitle("foooo");
        lol.setDescription("fuYOu");
        return lol;
    }

    public void addTask(final AddTaskInput addTaskInput) {
        Task task = new Task();
        task.setTitle(addTaskInput.getTitle());
        task.setDescription(addTaskInput.getDescription());

        tasksRepository.save(task);
    }

    public void deleteTask(final Long id) throws ItemNotFoundException {
        try {
            tasksRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Task with id = " + id + " not exist", e);
        }
    }

    public void editTask(final TaskDto taskDto) throws InvalidParametersException {
        if (!tasksRepository.existsById(taskDto.getId())) {
            throw new InvalidParametersException("Task with id = " + taskDto.getId() + " not exist");
        }

        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());

        tasksRepository.save(task);
    }
}