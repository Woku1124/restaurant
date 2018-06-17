package pl.zzpwjj.restaurant.core.employees.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.employees.model.dto.EmployeeDto;
import pl.zzpwjj.restaurant.core.employees.model.entities.Employee;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeesConverter {

    public EmployeeDto convertEmployee(final Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setPesel(employee.getPesel());
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setSalary(employee.getSalary());

        return employeeDto;
    }

    public List<EmployeeDto> convertEmployees(final List<Employee> employees) {
        return employees.stream().map(this::convertEmployee).collect(Collectors.toList());
    }
}
