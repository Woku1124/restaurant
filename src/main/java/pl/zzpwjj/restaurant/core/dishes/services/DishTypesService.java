package pl.zzpwjj.restaurant.core.dishes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.dishes.model.dto.DishTypeDto;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;
import pl.zzpwjj.restaurant.core.dishes.model.entities.DishType;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishTypeInput;
import pl.zzpwjj.restaurant.core.dishes.repositories.DishTypesRepository;

import java.util.List;

@Service
public class DishTypesService {
    private DishTypesRepository dishTypesRepository;
    private DishesService dishesService;

    @Autowired
    public DishTypesService(final DishTypesRepository dishTypesRepository, final DishesService dishesService) {
        this.dishTypesRepository = dishTypesRepository;
        this.dishesService = dishesService;
    }

    public List<DishType> getDishTypes() {
        return dishTypesRepository.findAll();
    }

    public DishType getDishType(final Long id) throws ItemNotFoundException {
        return dishTypesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }
    public DishType getDishTypeByName(final String name) {
        return dishTypesRepository.findByName(name);
    }

    public DishType addDishType(final AddDishTypeInput addDishTypeInput) {
        DishType dishType = new DishType();
        dishType.setName(addDishTypeInput.getName());

        return dishTypesRepository.save(dishType);
    }

    public void deleteDishType(final Long id) throws ItemNotFoundException {
        if (!dishTypesRepository.existsById(id)) {
            throw new ItemNotFoundException("DishType with id = " + id + " does not exist");
        }
        List<Dish> dishes = dishesService.getDishesWithTypeIdEqualsTo(id);
        for(int i = 0; i < dishes.size(); i++){
            dishesService.deleteDish(dishes.get(i).getId());
        }
        dishTypesRepository.deleteById(id);
    }

    public DishType editDishType(final DishTypeDto dishTypeDto) throws ItemNotFoundException {
        if (!dishTypesRepository.existsById(dishTypeDto.getId())) {
            throw new ItemNotFoundException("DishType with id = " + dishTypeDto.getId() + " does not exist");
        }

        DishType dishType = new DishType();
        dishType.setId(dishTypeDto.getId());
        dishType.setName(dishTypeDto.getName());

        return dishTypesRepository.save(dishType);
    }
}
