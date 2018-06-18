package pl.zzpwjj.restaurant.core.foodOrders.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.finances.services.IncomesService;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddDishFoodOrderInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddFoodOrderInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.FoodOrderDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.FoodOrder;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.FoodOrdersRepository;
import pl.zzpwjj.restaurant.core.dishes.services.DishesService;
import pl.zzpwjj.restaurant.core.services.EmailSenderService;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
public class FoodOrdersService {

    private FoodOrdersRepository foodOrdersRepository;
    private AddressesService addressesService;
    private PersonalDatasService personalDatasService;
    private DishFoodOrdersService dishFoodOrdersService;
    private DishesService dishesService;
    private EmailSenderService emailSenderService;
    private IncomesService incomesService;

    @Autowired
    public FoodOrdersService(final FoodOrdersRepository foodOrdersRepository, final AddressesService addressesService,
                             final PersonalDatasService personalDatasService, final DishFoodOrdersService dishFoodOrdersService,
                             final DishesService dishesService, EmailSenderService emailSenderService, final IncomesService incomesService) {
        this.foodOrdersRepository = foodOrdersRepository;
        this.addressesService = addressesService;
        this.personalDatasService = personalDatasService;
        this.dishFoodOrdersService = dishFoodOrdersService;
        this.dishesService = dishesService;
        this.emailSenderService = emailSenderService;
        this.incomesService = incomesService;
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

    public FoodOrder addFoodOrder(final AddFoodOrderInput addFoodOrderInput)
            throws ItemNotFoundException, MessagingException {
        FoodOrder foodOrder = new FoodOrder();
        Double fullPrice = 0d;
        foodOrder.setPersonalData(personalDatasService.addPersonalData(addFoodOrderInput.getPersonalData()));
        foodOrder.setAddress(addressesService.addAddress(addFoodOrderInput.getAddress()));
        foodOrder.setDateOfOrder(LocalDate.now());
        foodOrder.setDateOfRealization(null);
        for(String dishName : addFoodOrderInput.getDishNames()) {
            try {
                fullPrice += dishesService.getDishByName(dishName).getPrice();
            } catch(ItemNotFoundException e){
                throw new ItemNotFoundException("Dish with name = " + dishName + " does not exist", e);
            }
        }

        foodOrder.setFull_price(fullPrice);

        FoodOrder order = foodOrdersRepository.save(foodOrder);

        for(String dishName : addFoodOrderInput.getDishNames()) {
            AddDishFoodOrderInput dishFoodOrder = new AddDishFoodOrderInput();
            dishFoodOrder.setDish(dishesService.getDishByName(dishName));
            dishFoodOrder.setFoodOrder(order);
            dishFoodOrdersService.addDishFoodOrder(dishFoodOrder);
        }

        emailSenderService.send(foodOrder.getAddress().getEmail(), "We received your order",
                    "Potwierdzamy złożenie zamówienia nr " + foodOrder.getId());
        incomesService.addIncome(fullPrice);
        return order;
    }

    @Transactional
    public void deleteFoodOrder(final Long id) throws ItemNotFoundException {
        if (!foodOrdersRepository.existsById(id)) {
            throw new ItemNotFoundException("Food order with id = " + id + " does not exist");
        }
            dishFoodOrdersService.deleteDishFoodOrdersByFoodOrderId(id);
            personalDatasService.deletePersonalData(foodOrdersRepository.findById(id).get().getPersonalData().getId());
            addressesService.deleteAddress(foodOrdersRepository.findById(id).get().getAddress().getId());
            foodOrdersRepository.deleteById(id);
    }

    public FoodOrder editFoodOrder(final FoodOrderDto foodOrderDto) throws ItemNotFoundException {
        if (!foodOrdersRepository.existsById(foodOrderDto.getId())) {
            throw new ItemNotFoundException("Food order with id = " + foodOrderDto.getId() + " does not exist");
        }
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setId(foodOrderDto.getId());
        foodOrder.setPersonalData(foodOrderDto.getPersonalData());
        foodOrder.setAddress(foodOrderDto.getAddress());
        foodOrder.setDateOfOrder(foodOrderDto.getDateOfOrder());
        foodOrder.setDateOfRealization(foodOrderDto.getDateOfRealization());
        foodOrder.setFull_price(foodOrderDto.getFull_price());

        return foodOrdersRepository.save(foodOrder);
    }

    public void realizeFoodOrder(final Long id) throws ItemNotFoundException, MessagingException, InvalidParametersException {
        if (!foodOrdersRepository.existsById(id)) {
            throw new ItemNotFoundException("Food order with id = " + id + " does not exist");
        }
        FoodOrder foodOrder = foodOrdersRepository.findById(id).get();
        if(foodOrder.getDateOfRealization() != null){
            throw new InvalidParametersException("Food order with id = " + id + " is already realized");
        }
        foodOrder.setDateOfRealization(LocalDate.now());
        foodOrdersRepository.save(foodOrder);
        emailSenderService.send(foodOrder.getAddress().getEmail(), "Your order was realized",
                    "We hope you will order from us again");

    }

}