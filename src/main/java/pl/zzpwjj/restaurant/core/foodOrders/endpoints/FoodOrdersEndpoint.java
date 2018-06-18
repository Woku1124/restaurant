package pl.zzpwjj.restaurant.core.foodOrders.endpoints;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.converters.FoodOrdersConverter;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.FoodOrderDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddFoodOrderInput;
import pl.zzpwjj.restaurant.core.foodOrders.services.FoodOrdersService;
import pl.zzpwjj.restaurant.core.foodOrders.validators.FoodOrderValidator;

@RequestMapping("/foodOrders")
@Api(value = "Food orders endpoint")
@RestController
public class FoodOrdersEndpoint {

    private FoodOrdersService foodOrdersService;
    private FoodOrdersConverter foodOrdersConverter;
    private FoodOrderValidator validator;

    @Autowired
    public FoodOrdersEndpoint(final FoodOrdersService foodOrdersService, final FoodOrdersConverter foodOrdersConverter,
                              final FoodOrderValidator validator) {
        this.foodOrdersService = foodOrdersService;
        this.foodOrdersConverter = foodOrdersConverter;
        this.validator = validator;
    }

    @ApiOperation(value = "Returns all food orders")
    @GetMapping("/getFoodOrders")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<FoodOrderDto>> getFoodOrders() {
        List<FoodOrderDto> foodOrderDtos = foodOrdersConverter.convertFoodOrders(foodOrdersService.getFoodOrders());
        return new ResponseEntity<>(foodOrderDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns food order")
    @GetMapping("/getFoodOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<FoodOrderDto> getFoodOrder(@RequestParam("id") @NotNull final Long id) {
        FoodOrderDto foodOrderDto;
        try {
            foodOrderDto = foodOrdersConverter.convertFoodOrder(foodOrdersService.getFoodOrder(id));
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodOrderDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns not realized food orders")
    @GetMapping("/getNotRealizedFoodOrders")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<FoodOrderDto>> getNotRealizedFoodOrders() {
        List<FoodOrderDto> foodOrderDtos = foodOrdersConverter.convertFoodOrders(foodOrdersService.getNotRealizedFoodOrders());
        return new ResponseEntity<>(foodOrderDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds food order")
    @PostMapping("/addFoodOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addFoodOrder(@RequestBody @Valid final AddFoodOrderInput addFoodOrderInput)
            throws ItemNotFoundException, MessagingException, InvalidParametersException {
        validator.validate(addFoodOrderInput);
        foodOrdersService.addFoodOrder(addFoodOrderInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes food order")
    @DeleteMapping("/deleteFoodOrder")
    public ResponseEntity<Void> deleteFoodOrder(@RequestParam("id") @NotNull final Long id) {
        try {
            foodOrdersService.deleteFoodOrder(id);
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits food order")
    @PostMapping("/editFoodOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editFoodOrder(@RequestBody @Valid final FoodOrderDto foodOrderDto) throws ItemNotFoundException {
            foodOrdersService.editFoodOrder(foodOrderDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Marks food order as realized")
    @PostMapping("/realizeFoodOrder")
    public ResponseEntity<Void> realizeFoodOrder(@RequestParam("id") @NotNull final Long id) throws MessagingException {
        try {
            foodOrdersService.realizeFoodOrder(id);
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}