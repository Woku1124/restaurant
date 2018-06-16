package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.dto.RestaurantDto;
import pl.zzpwjj.restaurant.core.model.entities.Restaurant;
import pl.zzpwjj.restaurant.core.model.inputs.AddRestaurantInput;
import pl.zzpwjj.restaurant.core.repositories.RestaurantsRepository;

import java.util.List;

@Service
public class RestaurantsServices {
    private RestaurantsRepository restaurantsRepository;

    @Autowired
    public RestaurantsServices(final RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantsRepository.findAll();
    }

    public Restaurant getRestaurant(final Long id) throws ItemNotFoundException {
        return restaurantsRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addRestaurant(final AddRestaurantInput addRestaurantInput) {
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress_id(addRestaurantInput.getAddress_id());

        restaurantsRepository.save(restaurant);
    }

    public void deleteRestaurant(final Long id) throws ItemNotFoundException {
        try {
            restaurantsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Restaurant with id = " + id + " does not exist", e);
        }
    }

    public void editRestaurant(final RestaurantDto restaurantDto) throws InvalidParametersException {
        if (!restaurantsRepository.existsById(restaurantDto.getId())) {
            throw new InvalidParametersException("Restaurant with id = " + restaurantDto.getId() + " does not exist");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantDto.getId());
        restaurant.setAddress_id(restaurantDto.getAddress_id());

        restaurantsRepository.save(restaurant);
    }

}
