package pl.zzpwjj.restaurant.core.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.converters.RestaurantsConverter;
import pl.zzpwjj.restaurant.core.model.dto.RestaurantDto;
import pl.zzpwjj.restaurant.core.model.inputs.AddRestaurantInput;
import pl.zzpwjj.restaurant.core.services.RestaurantsServices;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/restaurant")
@Api(value = "restaurant endpoint")
@RestController
public class RestaurantsEndpoint {
    private RestaurantsServices restaurantsServices;
    private RestaurantsConverter restaurantsConverter;

    @Autowired
    public RestaurantsEndpoint(final RestaurantsServices restaurantsServices, final RestaurantsConverter restaurantsConverter) {
        this.restaurantsServices = restaurantsServices;
        this.restaurantsConverter = restaurantsConverter;
    }

    @ApiOperation(value = "Returns all restaurants")
    @GetMapping("/getRestaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<RestaurantDto>> getRestaurant() {
        List<RestaurantDto> restaurantDtos = restaurantsConverter.convertRestaurants(restaurantsServices.getRestaurants());
        return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns restaurant")
    @GetMapping("/getRestaurant")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<RestaurantDto> getDishType(@RequestParam("id") @NotNull final Long id) {
        RestaurantDto restaurantDto;
        try {
            restaurantDto = restaurantsConverter.convertRestaurant(restaurantsServices.getRestaurant(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds restaurant")
    @PostMapping("/addRestaurant")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addRestaurant(@RequestBody @Valid final AddRestaurantInput addRestaurantInput) {
        restaurantsServices.addRestaurant(addRestaurantInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes restaurant")
    @DeleteMapping("/deleteRestaurant")
    public ResponseEntity<Void> deleteRestaurant(@RequestParam("id") @NotNull final Long id) {
        try {
            restaurantsServices.deleteRestaurant(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits restaurant")
    @PostMapping("/editRestaurant")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editRestaurant(@RequestBody @Valid final RestaurantDto restaurantDto) {
        try {
            restaurantsServices.editRestaurant(restaurantDto);
        } catch (InvalidParametersException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
