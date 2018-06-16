package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.dto.DishTypeDto;
import pl.zzpwjj.restaurant.core.model.entities.DishType;
import pl.zzpwjj.restaurant.core.model.inputs.AddDishTypeInput;
import pl.zzpwjj.restaurant.core.repositories.DishTypesRepository;

import java.util.List;

@Service
public class DishTypesServices {
    private DishTypesRepository dishTypesRepository;

    @Autowired
    public DishTypesServices(final DishTypesRepository dishTypesRepository) {
        this.dishTypesRepository = dishTypesRepository;
    }

    public List<DishType> getDishTypes() {
        return dishTypesRepository.findAll();
    }

    public DishType getDishType(final Long id) throws ItemNotFoundException {
        return dishTypesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addDishType(final AddDishTypeInput addDishTypeInput) {
        DishType dishType = new DishType();
        dishType.setName(addDishTypeInput.getName());

        dishTypesRepository.save(dishType);
    }

    public void deleteDishType(final Long id) throws ItemNotFoundException {
        try {
            dishTypesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("DishType with id = " + id + " does not exist", e);
        }
    }

    public void editDishType(final DishTypeDto dishTypeDto) throws InvalidParametersException {
        if (!dishTypesRepository.existsById(dishTypeDto.getId())) {
            throw new InvalidParametersException("DishType with id = " + dishTypeDto.getId() + " does not exist");
        }

        DishType dishType = new DishType();
        dishType.setId(dishTypeDto.getId());
        dishType.setName(dishTypeDto.getName());

        dishTypesRepository.save(dishType);
    }
}
