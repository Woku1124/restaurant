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
import pl.zzpwjj.restaurant.core.dishes.model.dto.DishDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;
import pl.zzpwjj.restaurant.core.dishes.model.entities.DishType;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Rating;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishInput;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishTypeInput;
import pl.zzpwjj.restaurant.core.dishes.repositories.DishesRepository;
import pl.zzpwjj.restaurant.core.dishes.services.DishTypesService;
import pl.zzpwjj.restaurant.core.dishes.services.DishesService;
import pl.zzpwjj.restaurant.core.dishes.services.RatingsService;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DishesServiceTest {

    @InjectMocks
    private DishesService dishesService;

    @Mock
    private DishesRepository dishesRepository;

    @Mock
    private RatingsService ratingsService;

    @Mock
    private DishTypesService dishTypesService;


    @Test
    public void shouldCreateDish() throws ItemNotFoundException {
        // given
        DishType dishType = new DishType();
        dishType.setName("Kolacja");
        dishType.setId(20l);

        AddDishTypeInput addDishTypeInput = new AddDishTypeInput();
        addDishTypeInput.setName("Kolacja");

        AddDishInput addDishInput = new AddDishInput();
        addDishInput.setDescription("Pycha");
        addDishInput.setDishTypeInput(addDishTypeInput);
        addDishInput.setName("Schabowy");
        addDishInput.setPrice(5d);

        // when
        Mockito.when(dishTypesService.getDishTypeByName("Kolacja")).thenReturn(dishType);
        Mockito.when(dishesRepository.save(Mockito.any(Dish.class))).then(AdditionalAnswers.returnsFirstArg());
        Dish createdDish = dishesService.addDish(addDishInput);

        // then
        Assert.assertEquals(addDishInput.getName(), createdDish.getName());
        Assert.assertEquals(addDishInput.getDescription(), createdDish.getDescription());
        Assert.assertEquals(addDishInput.getPrice(), createdDish.getPrice());
        Assert.assertEquals(addDishInput.getDishTypeInput().getName(), createdDish.getDishType().getName());
    }

    @Test
    public void shouldDeleteDish() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(ratingsService.getRatingsWithDishIdEqualsTo(id)).thenReturn(new ArrayList<Rating>());
        Mockito.when(dishesRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(dishesRepository).deleteById(id);
        dishesService.deleteDish(id);

        // then
        Mockito.verify(dishesRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeleteDishButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(ratingsService.getRatingsWithDishIdEqualsTo(id)).thenReturn(new ArrayList<Rating>());
        Mockito.when(dishesRepository.existsById(id)).thenReturn(false);
        dishesService.deleteDish(id);

        // then throw an exception
    }

    @Test
    public void shouldUpdateDishType() throws ItemNotFoundException, InvalidParametersException {
        // given
        DishType dishType = new DishType();
        dishType.setName("Kolacja");
        dishType.setId(20l);

        DishDto dishDto = new DishDto();
        dishDto.setDescription("Pycha");
        dishDto.setDishType(dishType);
        dishDto.setName("Schabowy");
        dishDto.setPrice(5d);


        // when
        Mockito.when(dishesRepository.existsById(dishDto.getId())).thenReturn(true);
        Mockito.when(dishesRepository.save(Mockito.any(Dish.class))).then(AdditionalAnswers.returnsFirstArg());
        Dish updatedDish = dishesService.editDish(dishDto);

        // then
        Assert.assertEquals(dishDto.getName(), updatedDish.getName());
        Assert.assertEquals(dishDto.getDescription(), updatedDish.getDescription());
        Assert.assertEquals(dishDto.getPrice(), updatedDish.getPrice());
        Assert.assertEquals(dishDto.getDishType(), updatedDish.getDishType());
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotUpdateDishTypeButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        DishType dishType = new DishType();
        dishType.setName("Kolacja");
        dishType.setId(20l);

        DishDto dishDto = new DishDto();
        dishDto.setDescription("Pycha");
        dishDto.setDishType(dishType);
        dishDto.setName("Schabowy");
        dishDto.setPrice(5d);


        // when
        Mockito.when(dishesRepository.existsById(dishDto.getId())).thenReturn(false);
        Dish updatedDish = dishesService.editDish(dishDto);

        // then throw an exception
    }

}
