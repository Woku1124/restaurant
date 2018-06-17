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

@Service
public class FoodOrdersService {

    private FoodOrdersRepository foodOrdersRepository;
    private AddressesService addressesService;
    private PersonalDatasService personalDatasService;
    private DishFoodOrdersService dishFoodOrdersService;
    private DishesService dishesService;

    @Autowired
    public FoodOrdersService(final FoodOrdersRepository foodOrdersRepository, final AddressesService addressesService,
                             final PersonalDatasService personalDatasService, final DishFoodOrdersService dishFoodOrdersService,
                             final DishesService dishesService) {
        this.foodOrdersRepository = foodOrdersRepository;
        this.addressesService = addressesService;
        this.personalDatasService = personalDatasService;
        this.dishFoodOrdersService = dishFoodOrdersService;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrdersRepository.findAll();
    }

    public List<FoodOrder> getNotRealizedFoodOrders() {
        return foodOrdersRepository.findAll();
    }


    public FoodOrder getFoodOrder(final Long id) throws ItemNotFoundException {
        return foodOrdersRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addFoodOrder(final AddFoodOrderInput addFoodOrderInput) throws ItemNotFoundException{
        FoodOrder foodOrder = new FoodOrder();
        Double fullPrice = 0d;
        foodOrder.setPersonal_data_id(personalDatasService.addPersonalData(addFoodOrderInput.getPersonal_data_id()));
        foodOrder.setAddress_id(addressesService.addAddress(addFoodOrderInput.getAddress_id()));
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
            dishFoodOrder.setDish_id(dishesService.getDishByName(dishName));
            dishFoodOrdersService.addDishFoodOrder(dishFoodOrder);
        }
    }

    public void deleteFoodOrder(final Long id) throws ItemNotFoundException {
        try {
            dishFoodOrdersService.deleteDishFoodOrdersByFoodOrderId(id);
            FoodOrder foodOrder = foodOrdersRepository.findById(id).get();
            foodOrdersRepository.deleteById(id);
            personalDatasService.deletePersonalData(foodOrder.getPersonal_data_id().getId());
            addressesService.deleteAddress(foodOrder.getAddress_id().getId());
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
        foodOrder.setPersonal_data_id(foodOrderDto.getPersonal_data_id());
        foodOrder.setAddress_id(foodOrderDto.getAddress_id());
        foodOrder.setDate_of_order(foodOrderDto.getDate_of_order());
        foodOrder.setDate_of_realization(foodOrderDto.getDate_of_realization());
        foodOrder.setFull_price(foodOrderDto.getFull_price());

        foodOrdersRepository.save(foodOrder);
    }

    public void realizeFoodOrder(final Long id) throws ItemNotFoundException {
        if (!foodOrdersRepository.existsById(id)) {
            throw new ItemNotFoundException("Food order with id = " + id + " does not exist");
        }
        FoodOrder foodOrder = foodOrdersRepository.findById(id).get();
        foodOrder.setDate_of_realization(LocalDate.now());
        foodOrdersRepository.save(foodOrder);
    }

}