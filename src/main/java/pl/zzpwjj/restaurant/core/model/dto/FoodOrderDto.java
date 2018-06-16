package pl.zzpwjj.restaurant.core.model.dto;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.model.entities.Address;
import pl.zzpwjj.restaurant.core.model.entities.PersonalData;
import pl.zzpwjj.restaurant.core.model.entities.Restaurant;

import java.util.Date;

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
    private Restaurant restaurant_id;

    @NotNull
    private Double full_price;

    @NotNull
    private Date date_of_order;

    private Date date_of_realization;
}
