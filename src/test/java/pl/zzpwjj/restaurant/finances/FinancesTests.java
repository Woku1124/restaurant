package pl.zzpwjj.restaurant.finances;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.employees.model.entities.Employee;
import pl.zzpwjj.restaurant.core.employees.repositories.EmployeesRepository;
import pl.zzpwjj.restaurant.core.employees.services.EmployeesService;
import pl.zzpwjj.restaurant.core.finances.model.entities.Income;
import pl.zzpwjj.restaurant.core.finances.model.entities.Outgoing;
import pl.zzpwjj.restaurant.core.finances.repositories.IncomesRepository;
import pl.zzpwjj.restaurant.core.finances.repositories.OutgoingsRepository;
import pl.zzpwjj.restaurant.core.finances.services.IncomesService;
import pl.zzpwjj.restaurant.core.finances.services.OutgoingsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FinancesTests {

    @InjectMocks
    private IncomesService incomesService;

    @InjectMocks
    private OutgoingsService outgoingsService;

    @Mock
    private IncomesRepository incomesRepository;

    @Mock
    private OutgoingsRepository outgoingsRepository;


    @Test
    public void shouldCreateIncome() {
        // given
        Income income = new Income();
        income.setValueOfIncome(100d);
        income.setDateOfIncome(LocalDateTime.of(2018,06,17,06,47));

        // when
        Mockito.when(incomesRepository.save(Mockito.any(Income.class))).then(AdditionalAnswers.returnsFirstArg());
        Income createdIncome = incomesService.addIncome(income.getValueOfIncome());
        createdIncome.setDateOfIncome(LocalDateTime.of(2018,06,17,06,47));

        // then
        Assert.assertEquals(income.getValueOfIncome(), createdIncome.getValueOfIncome());
        Assert.assertEquals(income.getDateOfIncome(), createdIncome.getDateOfIncome());

    }

    @Test
    public void shouldCreateOutgoing() {
        // given
        Outgoing outgoing = new Outgoing();
        outgoing.setValueOfOutgoing(100d);
        outgoing.setPesel("90909090999");
        outgoing.setTypeOfOutgoing("salary");
        outgoing.setDateOfOutgoing(LocalDateTime.of(2018,06,17,06,47));

        // when
        Mockito.when(outgoingsRepository.save(Mockito.any(Outgoing.class))).then(AdditionalAnswers.returnsFirstArg());
        Outgoing createdOutgoing = outgoingsService.addSalaryOutgoing(outgoing.getPesel(),outgoing.getValueOfOutgoing());
        createdOutgoing.setDateOfOutgoing(LocalDateTime.of(2018,06,17,06,47));

        // then
        Assert.assertEquals(outgoing.getValueOfOutgoing(), createdOutgoing.getValueOfOutgoing());
        Assert.assertEquals(outgoing.getDateOfOutgoing(), createdOutgoing.getDateOfOutgoing());

    }
}
