package pl.zzpwjj.restaurant.core.employees.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.employees.model.entities.Employee;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {
}
