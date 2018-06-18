package pl.zzpwjj.restaurant.dishes;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.dishes.model.dto.RatingDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;
import pl.zzpwjj.restaurant.core.dishes.model.entities.DishType;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Rating;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddRatingInput;
import pl.zzpwjj.restaurant.core.dishes.repositories.RatingsRepository;
import pl.zzpwjj.restaurant.core.dishes.services.DishesService;
import pl.zzpwjj.restaurant.core.dishes.services.RatingsService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RatingsServiceTest {

    @InjectMocks
    private RatingsService ratingsService;

    @Mock
    private RatingsRepository ratingsRepository;

    @Mock
    private DishesService dishesService;

    @Test
    public void shouldCreateRating() throws ItemNotFoundException {
        // given
        DishType dishType = new DishType();
        dishType.setName("Kolacja");
        dishType.setId(20l);

        Dish dish = new Dish();
        dish.setId(10l);
        dish.setDescription("Pycha");
        dish.setDishType(dishType);
        dish.setName("Schabowy");
        dish.setPrice(5d);

        AddRatingInput addRatingInput = new AddRatingInput();
        addRatingInput.setDishName("Schabowy");
        addRatingInput.setMark(3);
        addRatingInput.setComment("Dobreee");

        // when
        Mockito.when(dishesService.getDishByName("Schabowy")).thenReturn(dish);
        Mockito.when(ratingsRepository.save(Mockito.any(Rating.class))).then(AdditionalAnswers.returnsFirstArg());
        Rating createdRating = ratingsService.addRating(addRatingInput);

        // then
        Assert.assertEquals(addRatingInput.getDishName(), createdRating.getDish().getName());
        Assert.assertEquals(addRatingInput.getMark(), createdRating.getMark());
        Assert.assertEquals(addRatingInput.getComment(), createdRating.getComment());
    }


    @Test
    public void shouldDeleteRating() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(ratingsRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(ratingsRepository).deleteById(id);
        ratingsService.deleteRating(id);

        // then
        Mockito.verify(ratingsRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeleteRatingButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(ratingsRepository.existsById(id)).thenReturn(false);
        ratingsService.deleteRating(id);

        // then throw an exception
    }

    @Test
    public void shouldUpdateRating() throws ItemNotFoundException, InvalidParametersException {
        // given
        DishType dishType = new DishType();
        dishType.setName("Kolacja");
        dishType.setId(20l);

        Dish dish = new Dish();
        dish.setId(10l);
        dish.setDescription("Pycha");
        dish.setDishType(dishType);
        dish.setName("Schabowy");
        dish.setPrice(5d);

        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(1l);
        ratingDto.setDish(dish);
        ratingDto.setMark(3);
        ratingDto.setComment("Dobreee");


        // when
        Mockito.when(ratingsRepository.existsById(ratingDto.getId())).thenReturn(true);
        Mockito.when(ratingsRepository.save(Mockito.any(Rating.class))).then(AdditionalAnswers.returnsFirstArg());
        Rating updatedRating = ratingsService.editRating(ratingDto);

        // then
        Assert.assertEquals(ratingDto.getDish(), updatedRating.getDish());
        Assert.assertEquals(ratingDto.getMark(), updatedRating.getMark());
        Assert.assertEquals(ratingDto.getComment(), updatedRating.getComment());
}

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotUpdateRatingButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        DishType dishType = new DishType();
        dishType.setName("Kolacja");
        dishType.setId(20l);

        Dish dish = new Dish();
        dish.setId(10l);
        dish.setDescription("Pycha");
        dish.setDishType(dishType);
        dish.setName("Schabowy");
        dish.setPrice(5d);

        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(1l);
        ratingDto.setDish(dish);
        ratingDto.setMark(3);
        ratingDto.setComment("Dobreee");

        // when
        Mockito.when(ratingsRepository.existsById(ratingDto.getId())).thenReturn(false);
        Rating updatedRating = ratingsService.editRating(ratingDto);

        // then throw an exception
    }
}
