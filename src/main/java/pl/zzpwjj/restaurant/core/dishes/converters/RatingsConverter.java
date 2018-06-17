package pl.zzpwjj.restaurant.core.dishes.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.dishes.model.dto.RatingDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Rating;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RatingsConverter {

    public RatingDto convertRating(final Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(rating.getId());
        ratingDto.setDish(rating.getDish());
        ratingDto.setMark(rating.getMark());
        ratingDto.setComment(rating.getComment());
        return ratingDto;
    }

    public List<RatingDto> convertRatings(final List<Rating> ratings) {
        return ratings.stream().map(this::convertRating).collect(Collectors.toList());
    }
}
