package pl.zzpwjj.restaurant.core.employees.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.employees.converters.EmployeesConverter;
import pl.zzpwjj.restaurant.core.employees.model.dto.EmployeeDto;
import pl.zzpwjj.restaurant.core.employees.model.inputs.AddEmployeeInput;
import pl.zzpwjj.restaurant.core.employees.services.EmployeesService;
import pl.zzpwjj.restaurant.core.employees.validators.EmployeesValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/employees")
@Api(value = "Employees endpoint")
@RestController
public class EmployeesEndpoint {

    private EmployeesService employeesService;
    private EmployeesConverter employeesConverter;
    private EmployeesValidator employeesValidator;

    @Autowired
    public EmployeesEndpoint(final EmployeesService employeesService, final EmployeesConverter employeesConverter,
                             final EmployeesValidator employeesValidator) {
        this.employeesService = employeesService;
        this.employeesConverter = employeesConverter;
        this.employeesValidator = employeesValidator;
    }

    @ApiOperation(value = "Returns all employees")
    @GetMapping("/getEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<EmployeeDto>> getTasks() {
        List<EmployeeDto> employees = employeesConverter.convertEmployees(employeesService.getEmployees());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns an employee")
    @GetMapping("/getEmployee")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<EmployeeDto> getEmployee(@RequestParam("id") @NotNull final Long id) {
        EmployeeDto employee;
        try {
            employee = employeesConverter.convertEmployee(employeesService.getEmployee(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds an employee")
    @PostMapping("/addEmployee")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addEmployee(@RequestBody @Valid final AddEmployeeInput employeeDto) {
        employeesService.addEmployee(employeeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes an employee")
    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<Void> deleteEmployee(@RequestParam("id") @NotNull final Long id) {
        try {
            employeesService.deleteEmployee(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits an employee")
    @PostMapping("/editEmployee")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editEmployee(@RequestBody @Valid final EmployeeDto employeeDto) {
        try {
            employeesService.editEmployee(employeeDto);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Returns sum of salaries")
    @GetMapping("/getSumOfSalaries")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Double> getSumOfSalaries() {
        Double sumOfSalaries = employeesService.getSumOfSalaries();
        return new ResponseEntity<>(sumOfSalaries, HttpStatus.OK);
    }
}