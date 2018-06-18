package pl.zzpwjj.restaurant.core.finances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.finances.model.entities.Income;
import pl.zzpwjj.restaurant.core.finances.repositories.IncomesRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomesService {

    private IncomesRepository incomesRepository;

    @Autowired
    public IncomesService(final IncomesRepository incomesRepository) {
        this.incomesRepository = incomesRepository;
    }

    public List<Income> getIncomes() {
        return incomesRepository.findAll();
    }

    public Income getIncome(final Long id) throws ItemNotFoundException {
        return incomesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Income addIncome(final Double valueOfIncome) {
        Income income = new Income();
        income.setValueOfIncome(valueOfIncome);
        income.setDateOfIncome(LocalDateTime.now());

        return incomesRepository.save(income);
    }
}
