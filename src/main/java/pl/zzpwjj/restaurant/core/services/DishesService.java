package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.dto.DishDto;
import pl.zzpwjj.restaurant.core.model.entities.Dish;
import pl.zzpwjj.restaurant.core.model.inputs.AddDishInput;
import pl.zzpwjj.restaurant.core.repositories.DishesRepository;

import java.util.List;

@Service
public class DishesService {
    private DishesRepository dishesRepository;

    @Autowired
    public DishesService(final DishesRepository dishesRepository) {
        this.dishesRepository = dishesRepository;
    }

    public List<Dish> getDishes() {
        return dishesRepository.findAll();
    }

    public Dish getDish(final Long id) throws ItemNotFoundException {
        return dishesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addDish(final AddDishInput addDishInput) {
        Dish dish = new Dish();
        dish.setDish_type_id(addDishInput.getDish_type_id());
        dish.setPrice(addDishInput.getPrice());
        dish.setName(addDishInput.getName());
        dish.setDescription(addDishInput.getDescription());

        dishesRepository.save(dish);
    }

    public void deleteDish(final Long id) throws ItemNotFoundException {
        try {
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
        dish.setDish_type_id(dishDto.getDish_type_id());
        dish.setPrice(dishDto.getPrice());
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());

        dishesRepository.save(dish);
    }

    public Dish getDishByName(final String name) throws ItemNotFoundException {
        return dishesRepository.findByName(name);
    }
}
