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
import pl.zzpwjj.restaurant.core.dishes.model.dto.DishTypeDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;
import pl.zzpwjj.restaurant.core.dishes.model.entities.DishType;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishTypeInput;
import pl.zzpwjj.restaurant.core.dishes.repositories.DishTypesRepository;
import pl.zzpwjj.restaurant.core.dishes.services.DishTypesService;
import pl.zzpwjj.restaurant.core.dishes.services.DishesService;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DishTypesServiceTest {

    @InjectMocks
    private DishTypesService dishTypesService;

    @Mock
    private DishTypesRepository dishTypesRepository;

    @Mock
    private DishesService dishesService;

    @Test
    public void shouldCreateDishType() {
        // given
        AddDishTypeInput addDishTypeInput = new AddDishTypeInput();
        addDishTypeInput.setName("Kolacja");


        // when
        Mockito.when(dishTypesRepository.save(Mockito.any(DishType.class))).then(AdditionalAnswers.returnsFirstArg());
        DishType createdDishType = dishTypesService.addDishType(addDishTypeInput);

        // then
        Assert.assertEquals(addDishTypeInput.getName(), createdDishType.getName());
    }


    @Test
    public void shouldDeleteDishType() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(dishesService.getDishesWithTypeIdEqualsTo(id)).thenReturn(new ArrayList<Dish>());
        Mockito.when(dishTypesRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(dishTypesRepository).deleteById(id);
        dishTypesService.deleteDishType(id);

        // then
        Mockito.verify(dishTypesRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeleteDishTypeButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(dishesService.getDishesWithTypeIdEqualsTo(id)).thenReturn(new ArrayList<Dish>());
        Mockito.when(dishTypesRepository.existsById(id)).thenReturn(false);
        dishTypesService.deleteDishType(id);

        // then throw an exception
    }

    @Test
    public void shouldUpdateDishType() throws ItemNotFoundException, InvalidParametersException {
        // given
        DishTypeDto dishTypeDto = new DishTypeDto();
        dishTypeDto.setId(1l);
        dishTypeDto.setName("Kolacja");


        // when
        Mockito.when(dishTypesRepository.existsById(dishTypeDto.getId())).thenReturn(true);
        Mockito.when(dishTypesRepository.save(Mockito.any(DishType.class))).then(AdditionalAnswers.returnsFirstArg());
        DishType updatedDishType = dishTypesService.editDishType(dishTypeDto);

        // then
        Assert.assertEquals(dishTypeDto.getName(), updatedDishType.getName());
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotUpdateDishTypeButThrowItemNotFoundException() throws ItemNotFoundException {
        // given

        DishTypeDto dishTypeDto = new DishTypeDto();
        dishTypeDto.setId(1l);
        dishTypeDto.setName("Kolacja");


        // when
        Mockito.when(dishTypesRepository.existsById(dishTypeDto.getId())).thenReturn(false);
        DishType updatedDishType = dishTypesService.editDishType(dishTypeDto);

        // then throw an exception
    }
}
