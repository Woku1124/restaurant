package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.dto.DishDto;
import pl.zzpwjj.restaurant.core.model.entities.Dish;
import pl.zzpwjj.restaurant.core.model.entities.Rating;
import pl.zzpwjj.restaurant.core.model.inputs.AddDishInput;
import pl.zzpwjj.restaurant.core.repositories.DishesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishesService {
    private DishesRepository dishesRepository;
    private RatingsService ratingsService;
    private DishFoodOrdersService dishFoodOrdersService;

    @Autowired
    public DishesService(final DishesRepository dishesRepository, final RatingsService ratingsService,
                         final DishFoodOrdersService dishFoodOrdersService) {
        this.dishesRepository = dishesRepository;
        this.ratingsService = ratingsService;
        this.dishFoodOrdersService = dishFoodOrdersService;
    }

    public List<Dish> getDishes() {
        return dishesRepository.findAll();
    }

    public Dish getDish(final Long id) throws ItemNotFoundException {
        return dishesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addDish(final AddDishInput addDishInput) {
        Dish dish = new Dish();
        dish.setDish_type(addDishInput.getDish_type());
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
        dish.setDish_type(dishDto.getDish_type());
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
