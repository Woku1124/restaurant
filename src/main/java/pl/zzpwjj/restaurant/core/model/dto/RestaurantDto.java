package pl.zzpwjj.restaurant.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.model.entities.Address;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    @NotNull
    private Long id;

    @NotNull
    private Address address_id;
}
