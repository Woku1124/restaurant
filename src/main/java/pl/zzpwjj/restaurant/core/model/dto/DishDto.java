package pl.zzpwjj.restaurant.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.model.entities.DishType;
import pl.zzpwjj.restaurant.core.model.entities.Restaurant;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    @NotNull
    private Long id;

    @NotNull
    private DishType dish_type_id;

    @NotNull
    private Restaurant restaurant_id;

    @NotNull
    private Double price;

    @NotNull
    private String name;

    @NotNull
    private String description;

}
