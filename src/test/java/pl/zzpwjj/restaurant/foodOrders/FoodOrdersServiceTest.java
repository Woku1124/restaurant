package pl.zzpwjj.restaurant.foodOrders;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;
import pl.zzpwjj.restaurant.core.dishes.services.DishesService;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.FoodOrderDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.Address;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.DishFoodOrder;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.FoodOrder;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.PersonalData;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddAddressInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddDishFoodOrderInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddFoodOrderInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddPersonalDataInput;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.FoodOrdersRepository;
import pl.zzpwjj.restaurant.core.foodOrders.services.AddressesService;
import pl.zzpwjj.restaurant.core.foodOrders.services.DishFoodOrdersService;
import pl.zzpwjj.restaurant.core.foodOrders.services.FoodOrdersService;
import pl.zzpwjj.restaurant.core.foodOrders.services.PersonalDatasService;
import pl.zzpwjj.restaurant.core.services.EmailSenderService;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.Silent.class)
public class FoodOrdersServiceTest {
    @InjectMocks
    private FoodOrdersService foodOrdersService;

    @Mock
    private FoodOrdersRepository foodOrdersRepository;
    @Mock
    private PersonalDatasService personalDatasService;
    @Mock
    private AddressesService addressesService;
    @Mock
    private DishesService dishesService;
    @Mock
    private DishFoodOrdersService dishFoodOrdersService;
    @Mock
    private EmailSenderService emailSenderService;


    @Test
    public void shouldCreateFoodOrder() throws MessagingException, ItemNotFoundException {
        // given
        AddFoodOrderInput order = new AddFoodOrderInput();
        AddPersonalDataInput personalDataInput = new AddPersonalDataInput();
        AddAddressInput addAddressInput = new AddAddressInput();
        PersonalData personalData = new PersonalData();
        Address address = new Address();
        List<String> dishNames = new ArrayList<>();
        String dishName = "DishOne";
        Dish dish = new Dish();
        dish.setName("dishName");
        dish.setPrice(20.5);
        dishNames.add(dishName);
        personalDataInput.setName("Kinga");
        personalData.setName("Kinga");
        personalDataInput.setSurname("Mirkiewicz");
        personalData.setSurname("Mirkiewicz");
        addAddressInput.setCity("Rawicz");
        address.setCity("Rawicz");
        addAddressInput.setEmail("203946@edu.p.lodz.pl");
        address.setEmail("203946@edu.p.lodz.pl");
        addAddressInput.setStreet("Piotrkowska");
        address.setStreet("Piotrkowska");
        addAddressInput.setHomeNr(2);
        address.setHomeNr(2);
        addAddressInput.setPhoneNr(123456789l);
        address.setPhoneNr(123456789l);
        order.setPersonalData(personalDataInput);
        order.setAddress(addAddressInput);
        order.setDishNames(dishNames);


        // when
        Mockito.when(foodOrdersRepository.save(Mockito.any(FoodOrder.class))).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(personalDatasService.addPersonalData(Mockito.any(AddPersonalDataInput.class))).thenReturn(personalData);
        Mockito.when(addressesService.addAddress(Mockito.any(AddAddressInput.class))).thenReturn(address);
        Mockito.when(dishesService.getDishByName(Mockito.any(String.class))).thenReturn(dish);
        Mockito.when(dishFoodOrdersService.addDishFoodOrder(Mockito.any(AddDishFoodOrderInput.class))).thenReturn(new DishFoodOrder());
        FoodOrder createdOrder = foodOrdersService.addFoodOrder(order);

        // then
        Assert.assertEquals(order.getPersonalData().getName(), createdOrder.getPersonalData().getName());
        Assert.assertEquals(order.getPersonalData().getSurname(), createdOrder.getPersonalData().getSurname());
        Assert.assertEquals(order.getAddress().getCity(), createdOrder.getAddress().getCity());
        Assert.assertEquals(order.getAddress().getEmail(), createdOrder.getAddress().getEmail());
        Assert.assertEquals(order.getAddress().getStreet(), createdOrder.getAddress().getStreet());
        Assert.assertEquals(order.getAddress().getHomeNr(), createdOrder.getAddress().getHomeNr());
        Assert.assertEquals(order.getAddress().getPhoneNr(), createdOrder.getAddress().getPhoneNr());
        Assert.assertEquals(order.getAddress().getFlatNr(), createdOrder.getAddress().getFlatNr());

    }

    @Test
    public void shouldDeleteFoodOrder() throws ItemNotFoundException {
        // given
        Long id = 1L;
        FoodOrder foodOrder = new FoodOrder();
        PersonalData personalData = new PersonalData();
        Address address = new Address();
        foodOrder.setId(id);
        Optional optional = Optional.of(foodOrder);
        personalData.setName("Kinga");
        personalData.setSurname("Mirkiewicz");
        address.setCity("Rawicz");
        address.setEmail("203946@edu.p.lodz.pl");
        address.setStreet("Piotrkowska");
        address.setHomeNr(2);
        address.setPhoneNr(123456789l);
        foodOrder.setPersonalData(personalData);
        foodOrder.setAddress(address);

        // when
        Mockito.doNothing().when(foodOrdersRepository).deleteById(id);
        Mockito.doNothing().when(addressesService).deleteAddress(Mockito.any(Long.class));
        Mockito.doNothing().when(personalDatasService).deletePersonalData(Mockito.any(Long.class));
        Mockito.when(foodOrdersRepository.existsById(id)).thenReturn(true);
        Mockito.when(foodOrdersRepository.findById(id)).thenReturn(optional);
        foodOrdersService.deleteFoodOrder(id);

        // then
        Mockito.verify(foodOrdersRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeleteFoodOrderButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;
        FoodOrder foodOrder = new FoodOrder();
        PersonalData personalData = new PersonalData();
        Address address = new Address();
        foodOrder.setId(id);
        Optional optional = Optional.of(foodOrder);
        personalData.setName("Kinga");
        personalData.setSurname("Mirkiewicz");
        address.setCity("Rawicz");
        address.setEmail("203946@edu.p.lodz.pl");
        address.setStreet("Piotrkowska");
        address.setHomeNr(2);
        address.setPhoneNr(123456789l);
        foodOrder.setPersonalData(personalData);
        foodOrder.setAddress(address);

        // when
        Mockito.doNothing().when(foodOrdersRepository).deleteById(id);
        Mockito.doNothing().when(addressesService).deleteAddress(Mockito.any(Long.class));
        Mockito.doNothing().when(personalDatasService).deletePersonalData(Mockito.any(Long.class));
        Mockito.when(foodOrdersRepository.existsById(id)).thenReturn(false);
        Mockito.when(foodOrdersRepository.findById(id)).thenReturn(optional);
        foodOrdersService.deleteFoodOrder(id);

        // then throw an exception
    }

    @Test
    public void shouldUpdateFoodOrder() throws ItemNotFoundException {
        // given
        FoodOrderDto foodOrder = new FoodOrderDto();
        PersonalData personalData = new PersonalData();
        Address address = new Address();
        foodOrder.setId(1l);
        personalData.setName("Kinga");
        personalData.setSurname("Mirkiewicz");
        address.setCity("Rawicz");
        address.setEmail("203946@edu.p.lodz.pl");
        address.setStreet("Piotrkowska");
        address.setHomeNr(2);
        address.setPhoneNr(123456789l);
        foodOrder.setPersonalData(personalData);
        foodOrder.setAddress(address);

        // when
        Mockito.when(foodOrdersRepository.existsById(foodOrder.getId())).thenReturn(true);
        Mockito.when(foodOrdersRepository.save(Mockito.any(FoodOrder.class))).then(AdditionalAnswers.returnsFirstArg());
        FoodOrder updatedFoodOrder = foodOrdersService.editFoodOrder(foodOrder);

        // then
        Assert.assertEquals(foodOrder.getPersonalData().getName(), updatedFoodOrder.getPersonalData().getName());
        Assert.assertEquals(foodOrder.getPersonalData().getSurname(), updatedFoodOrder.getPersonalData().getSurname());
        Assert.assertEquals(foodOrder.getAddress().getCity(), updatedFoodOrder.getAddress().getCity());
        Assert.assertEquals(foodOrder.getAddress().getEmail(), updatedFoodOrder.getAddress().getEmail());
        Assert.assertEquals(foodOrder.getAddress().getStreet(), updatedFoodOrder.getAddress().getStreet());
        Assert.assertEquals(foodOrder.getAddress().getHomeNr(), updatedFoodOrder.getAddress().getHomeNr());
        Assert.assertEquals(foodOrder.getAddress().getPhoneNr(), updatedFoodOrder.getAddress().getPhoneNr());
        Assert.assertEquals(foodOrder.getAddress().getFlatNr(), updatedFoodOrder.getAddress().getFlatNr());
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotUpdateFoodOrderButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        FoodOrderDto foodOrder = new FoodOrderDto();
        PersonalData personalData = new PersonalData();
        Address address = new Address();
        foodOrder.setId(1l);
        personalData.setName("Kinga");
        personalData.setSurname("Mirkiewicz");
        address.setCity("Rawicz");
        address.setEmail("203946@edu.p.lodz.pl");
        address.setStreet("Piotrkowska");
        address.setHomeNr(2);
        address.setPhoneNr(123456789l);
        foodOrder.setPersonalData(personalData);
        foodOrder.setAddress(address);

        // when
        Mockito.when(foodOrdersRepository.existsById(address.getId())).thenReturn(false);
        FoodOrder updatedFoodOrder = foodOrdersService.editFoodOrder(foodOrder);

        // then throw an exception
    }

}