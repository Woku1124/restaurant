package pl.zzpwjj.restaurant.core.foodOrders.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.Address;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.PersonalData;

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
    private PersonalData personalData;

    @NotNull
    private Address address;

    @NotNull
    private Double full_price;

    @NotNull
    private LocalDate dateOfOrder;

    private LocalDate dateOfRealization;
}
