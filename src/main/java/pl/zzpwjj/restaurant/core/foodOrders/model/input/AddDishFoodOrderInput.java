package pl.zzpwjj.restaurant.core.foodOrders.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.FoodOrder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDishFoodOrderInput {
    @NotNull
    private FoodOrder foodOrder;

    @NotNull
    private Dish dish;

}
