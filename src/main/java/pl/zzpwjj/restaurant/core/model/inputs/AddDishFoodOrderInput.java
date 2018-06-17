package pl.zzpwjj.restaurant.core.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.zzpwjj.restaurant.core.model.entities.Dish;
import pl.zzpwjj.restaurant.core.model.entities.FoodOrder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDishFoodOrderInput {
    @NotNull
    private FoodOrder food_order;

    @NotNull
    private Dish dish;

}
