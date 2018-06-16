package pl.zzpwjj.restaurant.core.model.inputs;

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
public class AddDishInput {

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