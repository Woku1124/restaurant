package pl.zzpwjj.restaurant.core.dishes.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.dishes.converters.DishTypeConverter;
import pl.zzpwjj.restaurant.core.dishes.model.dto.DishTypeDto;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishTypeInput;
import pl.zzpwjj.restaurant.core.dishes.services.DishTypesService;
import pl.zzpwjj.restaurant.core.dishes.validators.DishTypeValidator;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/dishType")
@Api(value = "dishName Type endpoint")
@RestController
public class DishTypeEndpoint {

    private DishTypesService dishTypesService;
    private DishTypeConverter dishTypeConverter;
    private DishTypeValidator dishTypeValidator;

    @Autowired
    public DishTypeEndpoint(final DishTypesService dishTypesService, final DishTypeConverter dishTypeConverter, final DishTypeValidator dishTypeValidator) {
        this.dishTypesService = dishTypesService;
        this.dishTypeConverter = dishTypeConverter;
        this.dishTypeValidator = dishTypeValidator;
    }

    @ApiOperation(value = "Returns all dishName types")
    @GetMapping("/getDishTypes")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<DishTypeDto>> getDishTypes() {
        List<DishTypeDto> dishTypeDtos = dishTypeConverter.convertDishTypes(dishTypesService.getDishTypes());
        return new ResponseEntity<>(dishTypeDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns dishName type")
    @GetMapping("/getDishType")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<DishTypeDto> getDishType(@RequestParam("id") @NotNull final Long id) {
        DishTypeDto dishTypeDto;
        try {
            dishTypeDto = dishTypeConverter.convertDishType(dishTypesService.getDishType(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dishTypeDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds dishName type")
    @PostMapping("/addDishType")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addDishType(@RequestBody @Valid final AddDishTypeInput addDishTypeInput) throws ItemNotFoundException, MessagingException, InvalidParametersException{
        dishTypeValidator.validate(addDishTypeInput);
        dishTypesService.addDishType(addDishTypeInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes dishName type")
    @DeleteMapping("/deleteDishType")
    public ResponseEntity<Void> deleteDishType(@RequestParam("id") @NotNull final Long id) {
        try {
            dishTypesService.deleteDishType(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits dishName type")
    @PostMapping("/editDishType")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editDishType(@RequestBody @Valid final DishTypeDto dishTypeDto) {
        try {
            dishTypesService.editDishType(dishTypeDto);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
