package pl.zzpwjj.restaurant.core.finances.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {

    @NotNull
    private Long id;

    @NotNull
    private Double valueOfIncome;

    @NotNull
    private LocalDateTime dateOfIncome;
}