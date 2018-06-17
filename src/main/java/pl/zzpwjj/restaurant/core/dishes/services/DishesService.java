package pl.zzpwjj.restaurant.core.dishes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.services.DishFoodOrdersService;
import pl.zzpwjj.restaurant.core.dishes.model.dto.DishDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Rating;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishInput;
import pl.zzpwjj.restaurant.core.dishes.repositories.DishesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishesService {
    private DishesRepository dishesRepository;
    private RatingsService ratingsService;
    private DishFoodOrdersService dishFoodOrdersService;
    private DishTypesServices dishTypesServices;

    @Autowired
    public DishesService(final DishesRepository dishesRepository, final RatingsService ratingsService,
                         final DishFoodOrdersService dishFoodOrdersService, @Lazy final DishTypesServices dishTypesServices) {
        this.dishesRepository = dishesRepository;
        this.ratingsService = ratingsService;
        this.dishFoodOrdersService = dishFoodOrdersService;
        this.dishTypesServices = dishTypesServices;
    }

    public List<Dish> getDishes() {
        return dishesRepository.findAll();
    }

    public List<Dish> getDishesWithTypeNameEqualsTo(String name) {
        return dishesRepository.findAllByDishType_Name(name);
    }

    public List<Dish> getDishesWithTypeIdEqualsTo(Long id) {
        return dishesRepository.findAllByDishType_Id(id);
    }

    public Dish getDish(final Long id) throws ItemNotFoundException {
        return dishesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addDish(final AddDishInput addDishInput) {
        Dish dish = new Dish();
        dish.setDishType(dishTypesServices.getDishTypeByName(addDishInput.getDishTypeInput().getName()));
        dish.setPrice(addDishInput.getPrice());
        dish.setName(addDishInput.getName());
        dish.setDescription(addDishInput.getDescription());

        dishesRepository.save(dish);
    }

    public void deleteDish(final Long id) throws ItemNotFoundException {
        try {
            List<Rating> ratings = ratingsService.getRatingsWithDishIdEqualsTo(id);
            for(int i = 0; i < ratings.size(); i++){
                ratingsService.deleteRating(ratings.get(i).getId());
            }
            dishesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Dish with id = " + id + " does not exist", e);
        }
    }

    public void editDish(final DishDto dishDto) throws InvalidParametersException {
        if (!dishesRepository.existsById(dishDto.getId())) {
            throw new InvalidParametersException("Dish with id = " + dishDto.getId() + " does not exist");
        }

        Dish dish = new Dish();
        dish.setId(dishDto.getId());
        dish.setDishType(dishDto.getDishType());
        dish.setPrice(dishDto.getPrice());
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());

        dishesRepository.save(dish);
    }

    public Dish getDishByName(final String name) throws ItemNotFoundException {
        return dishesRepository.findByName(name);
    }

    public List<Dish> getMostOrderedDish(){
        List <Dish> mostOrderedDish = new ArrayList<>();
        for(Long id : dishFoodOrdersService.getMostOrderedDishId()){
            mostOrderedDish.add(dishesRepository.findById(id).get());
        }
        return mostOrderedDish;
    }
}
