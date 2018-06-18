package pl.zzpwjj.restaurant.core.finances.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.finances.model.dto.IncomeDto;
import pl.zzpwjj.restaurant.core.finances.model.entities.Income;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncomesConverter {

    public IncomeDto convertIncome(final Income income) {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setId(income.getId());
        incomeDto.setValueOfIncome(income.getValueOfIncome());
        incomeDto.setDateOfIncome(income.getDateOfIncome());

        return incomeDto;
    }

    public List<IncomeDto> convertIncomes(final List<Income> incomes) {
        return incomes.stream().map(this::convertIncome).collect(Collectors.toList());
    }
}