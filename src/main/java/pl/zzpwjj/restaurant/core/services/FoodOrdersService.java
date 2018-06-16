package pl.zzpwjj.restaurant.core.services;

import java.time.LocalDate;
import java.util.List;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.entities.Address;
import pl.zzpwjj.restaurant.core.model.inputs.AddFoodOrderInput;
import pl.zzpwjj.restaurant.core.model.dto.FoodOrderDto;
import pl.zzpwjj.restaurant.core.model.entities.FoodOrder;
import pl.zzpwjj.restaurant.core.repositories.AddressesRepository;
import pl.zzpwjj.restaurant.core.repositories.FoodOrdersRepository;

@Service
public class FoodOrdersService {

    private FoodOrdersRepository foodOrdersRepository;
    private AddressesService addressesService;
    private PersonalDatasService personalDatasService;

    @Autowired
    public FoodOrdersService(final FoodOrdersRepository foodOrdersRepository, final AddressesService addressesService,
                             final PersonalDatasService personalDatasService) {
        this.foodOrdersRepository = foodOrdersRepository;
        this.addressesService = addressesService;
        this.personalDatasService = personalDatasService;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrdersRepository.findAll();
    }

    public FoodOrder getFoodOrder(final Long id) throws ItemNotFoundException {
        return foodOrdersRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addFoodOrder(final AddFoodOrderInput addFoodOrderInput) {
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setPersonal_data_id(personalDatasService.addPersonalData(addFoodOrderInput.getPersonal_data_id()));
        foodOrder.setAddress_id(addressesService.addAddress(addFoodOrderInput.getAddress_id()));
        foodOrder.setDate_of_order(LocalDate.now());
        foodOrder.setDate_of_realization(null);

        foodOrdersRepository.save(foodOrder);
    }

    public void deleteFoodOrder(final Long id) throws ItemNotFoundException {
        try {
            foodOrdersRepository.deleteById(id);
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
}