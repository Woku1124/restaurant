package pl.zzpwjj.restaurant.core.dishes.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.dishes.model.dto.DishDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishesConverter {
    public DishDto convertDish(final Dish dish) {
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setDishType(dish.getDishType());
        dishDto.setPrice(dish.getPrice());
        dishDto.setName(dish.getName());
        dishDto.setDescription(dish.getDescription());
        return dishDto;
    }

    public List<DishDto> convertDishes(final List<Dish> dishes) {
        return dishes.stream().map(this::convertDish).collect(Collectors.toList());
    }
}
