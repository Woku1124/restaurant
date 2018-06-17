package pl.zzpwjj.restaurant.core.foodOrders.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.model.entities.Dish;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.FoodOrder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishFoodOrderDto {

    @NotNull
    private Long id;

    @NotNull
    private FoodOrder foodOrder;

    @NotNull
    private Dish dish;
}
