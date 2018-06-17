package pl.zzpwjj.restaurant.foodOrders;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.FoodOrdersRepository;
import pl.zzpwjj.restaurant.core.foodOrders.services.FoodOrdersService;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FoodOrdersServiceTest {
    @InjectMocks
    private FoodOrdersService foodOrdersService;

    @Mock
    private FoodOrdersRepository foodOrdersRepository;

}