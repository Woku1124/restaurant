package pl.zzpwjj.restaurant.core.employees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
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

    public void addEmployee(final AddEmployeeInput addEmployeeInput) {
        Employee employee = new Employee();
        employee.setFirstName(addEmployeeInput.getFirstName());
        employee.setLastName(addEmployeeInput.getLastName());
        employee.setPesel(addEmployeeInput.getPesel());
        employee.setPosition(addEmployeeInput.getPosition());
        employee.setSalary(addEmployeeInput.getSalary());

        employeesRepository.save(employee);
    }

    public void deleteEmployee(final Long id) throws ItemNotFoundException {
        try {
            employeesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Employee with id = " + id + " not exist", e);
        }
    }

    public void editEmployee(final EmployeeDto employeeDto) throws InvalidParametersException {
        if (!employeesRepository.existsById(employeeDto.getId())) {
            throw new InvalidParametersException("Employee with id = " + employeeDto.getId() + " not exist");
        }

        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPesel(employeeDto.getPesel());
        employee.setPosition(employeeDto.getPosition());
        employee.setSalary(employeeDto.getSalary());

        employeesRepository.save(employee);
    }
}
