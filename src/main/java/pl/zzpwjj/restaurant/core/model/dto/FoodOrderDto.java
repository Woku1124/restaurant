package pl.zzpwjj.restaurant.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.model.entities.Address;
import pl.zzpwjj.restaurant.core.model.entities.PersonalData;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderDto {
    @NotNull
    private Long id;

    @NotNull
    private PersonalData personal_data_id;

    @NotNull
    private Address address_id;

    @NotNull
    private Double full_price;

    @NotNull
    private LocalDate date_of_order;

    private LocalDate date_of_realization;
}
