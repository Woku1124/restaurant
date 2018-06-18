package pl.zzpwjj.restaurant.core.finances.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "incomes")
public class Income {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Double valueOfIncome;

    @NotNull
    private LocalDateTime dateOfIncome;

}
