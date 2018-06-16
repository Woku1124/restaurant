package pl.zzpwjj.restaurant.core.converters;

import pl.zzpwjj.restaurant.core.model.dto.RestaurantDto;
import pl.zzpwjj.restaurant.core.model.dto.TaskDto;
import pl.zzpwjj.restaurant.core.model.entities.Restaurant;
import pl.zzpwjj.restaurant.core.model.entities.Task;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantsConverter {
    public RestaurantDto convertRestaurant(final Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setAddress_id(restaurant.getAddress_id());

        return restaurantDto;
    }

    public List<RestaurantDto> convertRestaurants(final List<Restaurant> restaurants) {
        return restaurants.stream().map(this::convertRestaurant).collect(Collectors.toList());
    }
}
