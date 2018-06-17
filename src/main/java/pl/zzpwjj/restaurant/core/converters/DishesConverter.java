package pl.zzpwjj.restaurant.core.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.model.dto.DishDto;
import pl.zzpwjj.restaurant.core.model.entities.Dish;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishesConverter {
    public DishDto convertDish(final Dish dish) {
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setDish_type(dish.getDish_type());
        dishDto.setPrice(dish.getPrice());
        dishDto.setName(dish.getName());
        dishDto.setDescription(dish.getDescription());
        return dishDto;
    }

    public List<DishDto> convertDishes(final List<Dish> dishes) {
        return dishes.stream().map(this::convertDish).collect(Collectors.toList());
    }
}
