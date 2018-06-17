package pl.zzpwjj.restaurant.core.foodOrders.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.DishFoodOrderDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.DishFoodOrder;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishFoodOrdersConverter {
    public DishFoodOrderDto convertDishFoodOrder(final DishFoodOrder dishFoodOrder) {
        DishFoodOrderDto dishFoodOrderDto = new DishFoodOrderDto();
        dishFoodOrderDto.setId(dishFoodOrder.getId());
        dishFoodOrderDto.setDish(dishFoodOrder.getDish());
        dishFoodOrderDto.setFoodOrder(dishFoodOrder.getFoodOrder());
        return dishFoodOrderDto;
    }

    public List<DishFoodOrderDto> convertDishFoodOrders(final List<DishFoodOrder> dishFoodOrders) {
        return dishFoodOrders.stream().map(this::convertDishFoodOrder).collect(Collectors.toList());
    }
}
