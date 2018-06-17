package pl.zzpwjj.restaurant.employees;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.employees.model.dto.EmployeeDto;
import pl.zzpwjj.restaurant.core.employees.model.entities.Employee;
import pl.zzpwjj.restaurant.core.employees.model.inputs.AddEmployeeInput;
import pl.zzpwjj.restaurant.core.employees.repositories.EmployeesRepository;
import pl.zzpwjj.restaurant.core.employees.services.EmployeesService;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeesTests {

    @InjectMocks
    private EmployeesService employeesService;

    @Mock
    private EmployeesRepository employeesRepository;

    @Test
    public void shouldCreateEmployee() throws InvalidParametersException {
        // given
        AddEmployeeInput employee = new AddEmployeeInput();
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setPesel("55555555555");
        employee.setPosition("pos");
        employee.setSalary(0.0);

        // when
        Mockito.when(employeesRepository.save(Mockito.any(Employee.class))).then(AdditionalAnswers.returnsFirstArg());
        Employee createdEmployee = employeesService.addEmployee(employee);

        // then
        Assert.assertEquals(employee.getFirstName(), createdEmployee.getFirstName());
        Assert.assertEquals(employee.getLastName(), createdEmployee.getLastName());
        Assert.assertEquals(employee.getPesel(), createdEmployee.getPesel());
        Assert.assertEquals(employee.getPosition(), createdEmployee.getPosition());
        Assert.assertEquals(employee.getSalary(), createdEmployee.getSalary(), 0);
    }

    @Test
    public void shouldDeleteEmployee() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(employeesRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(employeesRepository).deleteById(id);
        employeesService.deleteEmployee(id);

        // then
        Mockito.verify(employeesRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeleteEmployeeButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(employeesRepository.existsById(id)).thenReturn(false);
        employeesService.deleteEmployee(id);

        // then throw an exception
    }

    @Test
    public void shouldUpdateEmployee() throws ItemNotFoundException, InvalidParametersException {
        // given
        EmployeeDto employee = new EmployeeDto();
        employee.setId(1L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setPesel("55555555555");
        employee.setPosition("pos");
        employee.setSalary(0.0);

        // when
        Mockito.when(employeesRepository.existsById(employee.getId())).thenReturn(true);
        Mockito.when(employeesRepository.save(Mockito.any(Employee.class))).then(AdditionalAnswers.returnsFirstArg());
        Employee updatedEmployee = employeesService.editEmployee(employee);

        // then
        Assert.assertEquals(employee.getFirstName(), updatedEmployee.getFirstName());
        Assert.assertEquals(employee.getLastName(), updatedEmployee.getLastName());
        Assert.assertEquals(employee.getPesel(), updatedEmployee.getPesel());
        Assert.assertEquals(employee.getPosition(), updatedEmployee.getPosition());
        Assert.assertEquals(employee.getSalary(), updatedEmployee.getSalary(), 0);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotUpdateEmployeeButThrowItemNotFoundException() throws ItemNotFoundException, InvalidParametersException {
        // given
        EmployeeDto employee = new EmployeeDto();
        employee.setId(1L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setPesel("55555555555");
        employee.setPosition("pos");
        employee.setSalary(0.0);

        // when
        Mockito.when(employeesRepository.existsById(employee.getId())).thenReturn(false);
        Employee updatedEmployee = employeesService.editEmployee(employee);

        // then throw an exception
    }

    @Test
    public void shouldReturnSumOfSalaries() {
        // given
        List<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee();
        Employee e2 = new Employee();
        Employee e3 = new Employee();
        Double salary1 = 1500.0;
        Double salary2 = 2500.0;
        Double salary3 = 3500.0;
        e1.setSalary(salary1);
        e2.setSalary(salary2);
        e3.setSalary(salary3);
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);

        // when
        Mockito.when(employeesRepository.findAll()).thenReturn(employees);
        Double sumOfSalaries = employeesService.getSumOfSalaries();

        // then
        Assert.assertEquals(salary1 + salary2 + salary3, sumOfSalaries, 0);
    }
}