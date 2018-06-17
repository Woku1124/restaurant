package pl.zzpwjj.restaurant.core.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.inputs.AddDishFoodOrderInput;
import pl.zzpwjj.restaurant.core.model.inputs.AddFoodOrderInput;
import pl.zzpwjj.restaurant.core.model.dto.FoodOrderDto;
import pl.zzpwjj.restaurant.core.model.entities.FoodOrder;
import pl.zzpwjj.restaurant.core.repositories.FoodOrdersRepository;

import javax.mail.MessagingException;

@Service
public class FoodOrdersService {

    private FoodOrdersRepository foodOrdersRepository;
    private AddressesService addressesService;
    private PersonalDatasService personalDatasService;
    private DishFoodOrdersService dishFoodOrdersService;
    private DishesService dishesService;
    private RestaurantEmailSenderService emailSenderService;

    @Autowired
    public FoodOrdersService(final FoodOrdersRepository foodOrdersRepository, final AddressesService addressesService,
                             final PersonalDatasService personalDatasService, final DishFoodOrdersService dishFoodOrdersService,
                             final DishesService dishesService, RestaurantEmailSenderService emailSenderService) {
        this.foodOrdersRepository = foodOrdersRepository;
        this.addressesService = addressesService;
        this.personalDatasService = personalDatasService;
        this.dishFoodOrdersService = dishFoodOrdersService;
        this.dishesService = dishesService;
        this.emailSenderService = emailSenderService;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrdersRepository.findAll();
    }

    public List<FoodOrder> getNotRealizedFoodOrders() {
        return foodOrdersRepository.findAllWhereDateOfRealizationIsNull();
    }


    public FoodOrder getFoodOrder(final Long id) throws ItemNotFoundException {
        return foodOrdersRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addFoodOrder(final AddFoodOrderInput addFoodOrderInput) throws ItemNotFoundException, MessagingException{
        FoodOrder foodOrder = new FoodOrder();
        Double fullPrice = 0d;
        foodOrder.setPersonal_data(personalDatasService.addPersonalData(addFoodOrderInput.getPersonal_data()));
        foodOrder.setAddress(addressesService.addAddress(addFoodOrderInput.getAddress()));
        foodOrder.setDate_of_order(LocalDate.now());
        foodOrder.setDate_of_realization(null);
        for(String dishName : addFoodOrderInput.getDish_names()) {
            try {
                fullPrice += dishesService.getDishByName(dishName).getPrice();
            } catch(ItemNotFoundException e){
                throw new ItemNotFoundException("Dish with name = " + dishName + " does not exist", e);
            }
        }

        foodOrder.setFull_price(fullPrice);

        foodOrdersRepository.save(foodOrder);

        for(String dishName : addFoodOrderInput.getDish_names()) {
            AddDishFoodOrderInput dishFoodOrder = new AddDishFoodOrderInput();
            dishFoodOrder.setDish(dishesService.getDishByName(dishName));
            dishFoodOrdersService.addDishFoodOrder(dishFoodOrder);
        }

        try {
            emailSenderService.sendEmail(foodOrder.getAddress().getEmail(), "We received your order",
                    "Potwierdzamy złożenie zamówienia nr " + foodOrder.getId());
        }
        catch(MessagingException e){
            throw new MessagingException("Couldn't send email to " + foodOrder.getAddress().getEmail(), e);
        }

    }

    public void deleteFoodOrder(final Long id) throws ItemNotFoundException {
        try {
            dishFoodOrdersService.deleteDishFoodOrdersByFoodOrderId(id);
            FoodOrder foodOrder = foodOrdersRepository.findById(id).get();
            foodOrdersRepository.deleteById(id);
            personalDatasService.deletePersonalData(foodOrder.getPersonal_data().getId());
            addressesService.deleteAddress(foodOrder.getAddress().getId());
        }
        catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Food order with id = " + id + " does not exist", e);
        }
    }

    public void editFoodOrder(final FoodOrderDto foodOrderDto) throws InvalidParametersException {
        if (!foodOrdersRepository.existsById(foodOrderDto.getId())) {
            throw new InvalidParametersException("Food order with id = " + foodOrderDto.getId() + " does not exist");
        }
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setId(foodOrderDto.getId());
        foodOrder.setPersonal_data(foodOrderDto.getPersonal_data());
        foodOrder.setAddress(foodOrderDto.getAddress());
        foodOrder.setDate_of_order(foodOrderDto.getDate_of_order());
        foodOrder.setDate_of_realization(foodOrderDto.getDate_of_realization());
        foodOrder.setFull_price(foodOrderDto.getFull_price());

        foodOrdersRepository.save(foodOrder);
    }

    public void realizeFoodOrder(final Long id) throws ItemNotFoundException, MessagingException {
        if (!foodOrdersRepository.existsById(id)) {
            throw new ItemNotFoundException("Food order with id = " + id + " does not exist");
        }
        FoodOrder foodOrder = foodOrdersRepository.findById(id).get();
        foodOrder.setDate_of_realization(LocalDate.now());
        foodOrdersRepository.save(foodOrder);
        try {
            emailSenderService.sendEmail(foodOrder.getAddress().getEmail(), "Your order was realized",
                    "We hope you will order from us again");
        }
        catch(MessagingException e){
            throw new MessagingException("Couldn't send email to " + foodOrder.getAddress().getEmail(), e);
        }
    }

}