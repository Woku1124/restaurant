package pl.zzpwjj.restaurant.foodOrders;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.DishFoodOrderRepository;
import pl.zzpwjj.restaurant.core.foodOrders.services.DishFoodOrdersService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DishFoodOrdersServiceTest {
    @InjectMocks
    private DishFoodOrdersService dishFoodOrdersService;

    @Mock
    private DishFoodOrderRepository dishFoodOrderRepository;

    @Test
    public void shouldDeleteDishFoodOrder() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(dishFoodOrderRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(dishFoodOrderRepository).deleteById(id);
        dishFoodOrdersService.deleteDishFoodOrder(id);

        // then
        Mockito.verify(dishFoodOrderRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeleteDishFoodOrderButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(dishFoodOrderRepository.existsById(id)).thenReturn(false);
        dishFoodOrdersService.deleteDishFoodOrder(id);

        // then throw an exception
    }

}