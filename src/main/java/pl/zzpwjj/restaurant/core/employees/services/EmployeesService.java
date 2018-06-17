package pl.zzpwjj.restaurant.core.employees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.employees.model.dto.EmployeeDto;
import pl.zzpwjj.restaurant.core.employees.model.entities.Employee;
import pl.zzpwjj.restaurant.core.employees.model.inputs.AddEmployeeInput;
import pl.zzpwjj.restaurant.core.employees.repositories.EmployeesRepository;

import java.util.List;

@Service
public class EmployeesService {

    private EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(final EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public List<Employee> getEmployees() {
        return employeesRepository.findAll();
    }

    public Employee getEmployee(final Long id) throws ItemNotFoundException {
        return employeesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Employee addEmployee(final AddEmployeeInput addEmployeeInput) {
        Employee employee = new Employee();
        employee.setFirstName(addEmployeeInput.getFirstName());
        employee.setLastName(addEmployeeInput.getLastName());
        employee.setPesel(addEmployeeInput.getPesel());
        employee.setPosition(addEmployeeInput.getPosition());
        employee.setSalary(addEmployeeInput.getSalary());

        return employeesRepository.save(employee);
    }

    public void deleteEmployee(final Long id) throws ItemNotFoundException {
        if (!employeesRepository.existsById(id)) {
            throw new ItemNotFoundException("Employee with id = " + id + " not exist");
        }

        employeesRepository.deleteById(id);
    }

    public Employee editEmployee(final EmployeeDto employeeDto) throws ItemNotFoundException {
        if (!employeesRepository.existsById(employeeDto.getId())) {
            throw new ItemNotFoundException("Employee with id = " + employeeDto.getId() + " not exist");
        }

        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPesel(employeeDto.getPesel());
        employee.setPosition(employeeDto.getPosition());
        employee.setSalary(employeeDto.getSalary());

        return employeesRepository.save(employee);
    }

    public Double getSumOfSalaries() {
        return employeesRepository.findAll().stream().mapToDouble(Employee::getSalary).sum();
    }
}
