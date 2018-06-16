package pl.zzpwjj.restaurant.core.converters;

import pl.zzpwjj.restaurant.core.model.dto.AddressDto;
import pl.zzpwjj.restaurant.core.model.dto.DishFoodOrderDto;
import pl.zzpwjj.restaurant.core.model.entities.Address;
import pl.zzpwjj.restaurant.core.model.entities.DishFoodOrder;

import java.util.List;
import java.util.stream.Collectors;

public class DishFoodOrdersConverter {
    public DishFoodOrderDto convertDishFoodOrder(final DishFoodOrder dishFoodOrder) {
        DishFoodOrderDto dishFoodOrderDto = new DishFoodOrderDto();
        dishFoodOrderDto.setId(dishFoodOrder.getId());
        dishFoodOrderDto.setDish_id(dishFoodOrder.getDish_id());
        dishFoodOrderDto.setFood_order_id(dishFoodOrder.getFood_order_id());
        return dishFoodOrderDto;
    }

    public List<DishFoodOrderDto> convertDishFoodOrders(final List<DishFoodOrder> dishFoodOrders) {
        return dishFoodOrders.stream().map(this::convertDishFoodOrder).collect(Collectors.toList());
    }
}
