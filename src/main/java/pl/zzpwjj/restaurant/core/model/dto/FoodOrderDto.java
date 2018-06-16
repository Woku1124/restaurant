package pl.zzpwjj.restaurant.core.model.dto;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderDto {
    @NotNull
    private Long id;

    @NotNull
    private PersonalDataDto personal_data_id;

    @NotNull
    private AddressDto address_id;

    @NotNull
    private Double full_price;

    @NotNull
    private Date date_of_order;

    private Date date_of_realization;
}
