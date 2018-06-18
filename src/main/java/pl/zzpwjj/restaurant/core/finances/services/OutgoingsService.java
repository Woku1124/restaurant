package pl.zzpwjj.restaurant.core.finances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.employees.services.EmployeesService;
import pl.zzpwjj.restaurant.core.finances.model.entities.Outgoing;
import pl.zzpwjj.restaurant.core.finances.repositories.OutgoingsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutgoingsService {
    private OutgoingsRepository outgoingsRepository;
    private EmployeesService employeesService;

    @Autowired
    public OutgoingsService(final OutgoingsRepository outgoingsRepository, EmployeesService employeesService) {
        this.outgoingsRepository = outgoingsRepository;
        this.employeesService = employeesService;
    }

    public List<Outgoing> getOutgoings() {
        return outgoingsRepository.findAll();
    }

    public Outgoing getOutgoing(final Long id) throws ItemNotFoundException {
        return outgoingsRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }
    public Outgoing addSalaryOutgoing(String pesel, Double valueOfOutgoing) {
        Outgoing outgoing = new Outgoing();
        outgoing.setPesel(pesel);
        outgoing.setTypeOfOutgoing("Salary");
        outgoing.setValueOfOutgoing(valueOfOutgoing);
        outgoing.setDateOfOutgoing(LocalDateTime.now());

        return outgoingsRepository.save(outgoing);
    }

    public void paySalaries() throws ItemNotFoundException {
        for(long i=0; i<employeesService.getEmployees().size(); i++) {
            addSalaryOutgoing(employeesService.getEmployee(i).getPesel(), employeesService.getEmployee(i).getSalary());
        }
    }
}
