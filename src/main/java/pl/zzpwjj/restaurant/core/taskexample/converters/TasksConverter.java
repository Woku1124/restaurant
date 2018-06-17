package pl.zzpwjj.restaurant.core.taskexample.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pl.zzpwjj.restaurant.core.taskexample.model.dto.TaskDto;
import pl.zzpwjj.restaurant.core.taskexample.model.entities.Task;

@Component
public class TasksConverter {

    public TaskDto convertTask(final Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        
        return taskDto;
    }

    public List<TaskDto> convertTasks(final List<Task> tasks) {
        return tasks.stream().map(this::convertTask).collect(Collectors.toList());
    }
}
