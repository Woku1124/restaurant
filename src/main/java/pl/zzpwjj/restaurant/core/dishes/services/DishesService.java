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
    private DishTypesService dishTypesService;

    @Autowired
    public DishesService(final DishesRepository dishesRepository, @Lazy final RatingsService ratingsService, @Lazy final DishTypesService dishTypesService) {
        this.dishesRepository = dishesRepository;
        this.ratingsService = ratingsService;
        this.dishTypesService = dishTypesService;
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

    public Dish addDish(final AddDishInput addDishInput) {
        Dish dish = new Dish();
        dish.setDishType(dishTypesService.getDishTypeByName(addDishInput.getDishTypeInput().getName()));
        dish.setPrice(addDishInput.getPrice());
        dish.setName(addDishInput.getName());
        dish.setDescription(addDishInput.getDescription());

        return dishesRepository.save(dish);
    }

    public void deleteDish(final Long id) throws ItemNotFoundException {

        if (!dishesRepository.existsById(id)) {
            throw new ItemNotFoundException("Address with id = " + id + " does not exist");
        }
        List<Rating> ratings = ratingsService.getRatingsWithDishIdEqualsTo(id);
        for(int i = 0; i < ratings.size(); i++){
            ratingsService.deleteRating(ratings.get(i).getId());
        }
        dishesRepository.deleteById(id);

    }

    public Dish editDish(final DishDto dishDto) throws ItemNotFoundException {
        if (!dishesRepository.existsById(dishDto.getId())) {
            throw new ItemNotFoundException("Dish with id = " + dishDto.getId() + " does not exist");
        }

        Dish dish = new Dish();
        dish.setId(dishDto.getId());
        dish.setDishType(dishDto.getDishType());
        dish.setPrice(dishDto.getPrice());
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());

       return dishesRepository.save(dish);
    }

    public Dish getDishByName(final String name) throws ItemNotFoundException {
        return dishesRepository.findByName(name);
    }

}
