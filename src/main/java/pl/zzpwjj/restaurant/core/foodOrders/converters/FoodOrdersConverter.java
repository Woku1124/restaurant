package pl.zzpwjj.restaurant.core.foodOrders.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.FoodOrderDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.FoodOrder;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodOrdersConverter {
    public FoodOrderDto convertFoodOrder(final FoodOrder foodOrder) {
        FoodOrderDto foodOrderDto = new FoodOrderDto();
        foodOrderDto.setId(foodOrder.getId());
        foodOrderDto.setPersonalData(foodOrder.getPersonalData());
        foodOrderDto.setAddress(foodOrder.getAddress());
        foodOrderDto.setDateOfOrder(foodOrder.getDateOfOrder());
        foodOrderDto.setDateOfRealization(foodOrder.getDateOfRealization());
        foodOrderDto.setFull_price(foodOrder.getFull_price());
        return foodOrderDto;
    }

    public List<FoodOrderDto> convertFoodOrders(final List<FoodOrder> foodOrders) {
        return foodOrders.stream().map(this::convertFoodOrder).collect(Collectors.toList());
    }
}
