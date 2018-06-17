package pl.zzpwjj.restaurant.core.dishes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.dishes.model.dto.RatingDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Rating;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddRatingInput;
import pl.zzpwjj.restaurant.core.dishes.repositories.RatingsRepository;

import java.util.List;

@Service
public class RatingsService {

    private RatingsRepository ratingsRepository;

    @Autowired
    public RatingsService(final RatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    public List<Rating> getRatings() {
        return ratingsRepository.findAll();
    }

    public List<Rating> getRatingsWithDishIdEqualsTo(final Long id) {
        return ratingsRepository.findAllByDish_Id(id);
    }

    public List<Rating> getRatingsWithDishNameEqualsTo(final String name) {
        return ratingsRepository.findAllByDish_Name(name);
    }

    public Rating getRating(final Long id) throws ItemNotFoundException {
        return ratingsRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Rating addRating(final AddRatingInput addRatingInput) {
        Rating rating = new Rating();
        rating.setDish(addRatingInput.getDish());
        rating.setMark(addRatingInput.getMark());
        rating.setComment(addRatingInput.getComment());

        return ratingsRepository.save(rating);
    }

    public void deleteRating(final Long id) throws ItemNotFoundException {
        try {
            ratingsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Rating with id = " + id + " does not exist", e);
        }
    }

    public void editRating(final RatingDto ratingDto) throws InvalidParametersException {
        if (!ratingsRepository.existsById(ratingDto.getId())) {
            throw new InvalidParametersException("Rating with id = " + ratingDto.getId() + " does not exist");
        }

        Rating rating = new Rating();
        rating.setId(ratingDto.getId());
        rating.setDish(ratingDto.getDish());
        rating.setMark(ratingDto.getMark());
        rating.setComment(ratingDto.getComment());

        ratingsRepository.save(rating);
    }
}
