package pl.zzpwjj.restaurant.core.converters;

import pl.zzpwjj.restaurant.core.model.dto.AddressDto;
import pl.zzpwjj.restaurant.core.model.dto.DishTypeDto;
import pl.zzpwjj.restaurant.core.model.entities.Address;
import pl.zzpwjj.restaurant.core.model.entities.DishType;

import java.util.List;
import java.util.stream.Collectors;

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
