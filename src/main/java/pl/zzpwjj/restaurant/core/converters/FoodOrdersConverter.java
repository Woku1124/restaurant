package pl.zzpwjj.restaurant.core.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.model.dto.FoodOrderDto;
import pl.zzpwjj.restaurant.core.model.entities.FoodOrder;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodOrdersConverter {
    public FoodOrderDto convertFoodOrder(final FoodOrder foodOrder) {
        FoodOrderDto foodOrderDto = new FoodOrderDto();
        foodOrderDto.setId(foodOrder.getId());
        foodOrderDto.setPersonal_data(foodOrder.getPersonal_data());
        foodOrderDto.setAddress(foodOrder.getAddress());
        foodOrderDto.setDate_of_order(foodOrder.getDate_of_order());
        foodOrderDto.setDate_of_realization(foodOrder.getDate_of_realization());
        foodOrderDto.setFull_price(foodOrder.getFull_price());
        return foodOrderDto;
    }

    public List<FoodOrderDto> convertFoodOrders(final List<FoodOrder> foodOrders) {
        return foodOrders.stream().map(this::convertFoodOrder).collect(Collectors.toList());
    }
}
