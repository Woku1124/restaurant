package pl.zzpwjj.restaurant.core.taskexample.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.taskexample.converters.TasksConverter;
import pl.zzpwjj.restaurant.core.taskexample.model.inputs.AddTaskInput;
import pl.zzpwjj.restaurant.core.taskexample.model.dto.TaskDto;
import pl.zzpwjj.restaurant.core.taskexample.services.TasksService;

@RequestMapping("/tasks")
@Api(value = "Tasks endpoint")
@RestController
public class TasksEndpoint {

    private TasksService tasksService;
    private TasksConverter tasksConverter;

    @Autowired
    public TasksEndpoint(final TasksService tasksService, final TasksConverter tasksConverter) {
        this.tasksService = tasksService;
        this.tasksConverter = tasksConverter;
    }

    @ApiOperation(value = "Returns all tasks")
    @GetMapping("/getTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<TaskDto> tasks = tasksConverter.convertTasks(tasksService.getTasks());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @ApiOperation(value = "Does **** all")
    @GetMapping("/nada")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<TaskDto>> nada() {
        List<TaskDto> tasks = new ArrayList<>();
        try {
            tasks.add(tasksConverter.convertTask(tasksService.getnada()));
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns task")
    @GetMapping("/getTask")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<TaskDto> getTask(@RequestParam("id") @NotNull final Long id) {
        TaskDto task;
        try {
            task = tasksConverter.convertTask(tasksService.getTask(id));
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds task")
    @PostMapping("/addTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addTask(@RequestBody @Valid final AddTaskInput taskDto) {
        tasksService.addTask(taskDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes task")
    @DeleteMapping("/deleteTask")
    public ResponseEntity<Void> deleteTask(@RequestParam("id") @NotNull final Long id) {
        try {
            tasksService.deleteTask(id);
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits task")
    @PostMapping("/editTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editTask(@RequestBody @Valid final TaskDto taskDto) {
        try {
            tasksService.editTask(taskDto);
        }
        catch (InvalidParametersException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}