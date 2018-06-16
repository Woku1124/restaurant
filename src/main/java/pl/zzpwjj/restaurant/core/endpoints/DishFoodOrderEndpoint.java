package pl.zzpwjj.restaurant.core.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.converters.DishFoodOrdersConverter;
import pl.zzpwjj.restaurant.core.model.dto.DishFoodOrderDto;
import pl.zzpwjj.restaurant.core.model.inputs.AddDishFoodOrderInput;
import pl.zzpwjj.restaurant.core.services.DishFoodOrdersService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/dishFoodOrders")
@Api(value = "dish food order endpoint")
@RestController
public class DishFoodOrderEndpoint {

    private DishFoodOrdersService dishFoodOrdersService;
    private DishFoodOrdersConverter dishFoodOrdersConverter;

    @Autowired
    public DishFoodOrderEndpoint(final DishFoodOrdersService dishFoodOrdersService, final DishFoodOrdersConverter dishFoodOrdersConverter) {
        this.dishFoodOrdersService = dishFoodOrdersService;
        this.dishFoodOrdersConverter = dishFoodOrdersConverter;
    }

    @ApiOperation(value = "Returns all dish food orders")
    @GetMapping("/getDishFoodOrders")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<DishFoodOrderDto>> getDishFoodOrders() {
        List<DishFoodOrderDto> dishFoodOrderDtos = dishFoodOrdersConverter.convertDishFoodOrders(dishFoodOrdersService.getDishFoodOrders());
        return new ResponseEntity<>(dishFoodOrderDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns dish food order")
    @GetMapping("/getDishFoodOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<DishFoodOrderDto> getDishFoodOrder(@RequestParam("id") @NotNull final Long id) {
        DishFoodOrderDto dishFoodOrderDto;
        try {
            dishFoodOrderDto = dishFoodOrdersConverter.convertDishFoodOrder(dishFoodOrdersService.getDishFoodOrder(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dishFoodOrderDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds dish food order")
    @PostMapping("/addDishFoodOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addDishFoodOrder(@RequestBody @Valid final AddDishFoodOrderInput addDishFoodOrderInput) {
        dishFoodOrdersService.addDishFoodOrder(addDishFoodOrderInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes dish food order")
    @DeleteMapping("/deleteDishFoodOrder")
    public ResponseEntity<Void> deleteDishFoodOrder(@RequestParam("id") @NotNull final Long id) {
        try {
            dishFoodOrdersService.deleteDishFoodOrder(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits dish food order")
    @PostMapping("/editDishFoodOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editDishFoodOrder(@RequestBody @Valid final DishFoodOrderDto dishFoodOrderDto) {
        try {
            dishFoodOrdersService.editDishFoodOrder(dishFoodOrderDto);
        } catch (InvalidParametersException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
