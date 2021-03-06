package pl.zzpwjj.restaurant.core.dishes.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.dishes.model.dto.DishTypeDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.DishType;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishTypeConverter {

    public DishTypeDto convertDishType(final DishType dishType) {
        DishTypeDto dishTypeDto = new DishTypeDto();
        dishTypeDto.setId(dishType.getId());
        dishTypeDto.setId(dishType.getId());
        return dishTypeDto;
    }

    public List<DishTypeDto> convertDishTypes(final List<DishType> dishTypes) {
        return dishTypes.stream().map(this::convertDishType).collect(Collectors.toList());
    }
}
