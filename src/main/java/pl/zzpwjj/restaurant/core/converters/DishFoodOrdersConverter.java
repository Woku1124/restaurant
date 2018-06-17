package pl.zzpwjj.restaurant.core.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.model.dto.DishFoodOrderDto;
import pl.zzpwjj.restaurant.core.model.entities.DishFoodOrder;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishFoodOrdersConverter {
    public DishFoodOrderDto convertDishFoodOrder(final DishFoodOrder dishFoodOrder) {
        DishFoodOrderDto dishFoodOrderDto = new DishFoodOrderDto();
        dishFoodOrderDto.setId(dishFoodOrder.getId());
        dishFoodOrderDto.setDish(dishFoodOrder.getDish());
        dishFoodOrderDto.setFood_order(dishFoodOrder.getFood_order());
        return dishFoodOrderDto;
    }

    public List<DishFoodOrderDto> convertDishFoodOrders(final List<DishFoodOrder> dishFoodOrders) {
        return dishFoodOrders.stream().map(this::convertDishFoodOrder).collect(Collectors.toList());
    }
}
