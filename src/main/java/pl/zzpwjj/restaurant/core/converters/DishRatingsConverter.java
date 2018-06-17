package pl.zzpwjj.restaurant.core.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.model.dto.DishRatingDto;
import pl.zzpwjj.restaurant.core.model.entities.DishRating;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishRatingsConverter {

    public DishRatingDto convertDishRating(final DishRating dishRating) {
        DishRatingDto dishRatingDto = new DishRatingDto();
        dishRatingDto.setId(dishRating.getId());
        dishRatingDto.setDish_id(dishRating.getDish_id());
        dishRatingDto.setRating(dishRating.getRating());
        return dishRatingDto;
    }

    public List<DishRatingDto> convertDishRatings(final List<DishRating> dishRatings) {
        return dishRatings.stream().map(this::convertDishRating).collect(Collectors.toList());
    }
}
