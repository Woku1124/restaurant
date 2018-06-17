package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.dto.RatingDto;
import pl.zzpwjj.restaurant.core.model.entities.Rating;
import pl.zzpwjj.restaurant.core.model.inputs.AddRatingInput;
import pl.zzpwjj.restaurant.core.repositories.RatingsRepository;

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
        return ratingsRepository.findAllWhereDishIdIsEqualTo(id);
    }

    public Rating getRating(final Long id) throws ItemNotFoundException {
        return ratingsRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Rating addRating(final AddRatingInput addRatingInput) {
        Rating rating = new Rating();
        rating.setDish_id(addRatingInput.getDish_id());
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
        rating.setDish_id(ratingDto.getDish_id());
        rating.setMark(ratingDto.getMark());
        rating.setComment(ratingDto.getComment());

        ratingsRepository.save(rating);
    }
}
