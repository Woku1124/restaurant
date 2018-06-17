package pl.zzpwjj.restaurant.core.employees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.core.employees.model.entities.Employee;
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
}
