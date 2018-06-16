package pl.zzpwjj.restaurant.core.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.converters.DishesConverter;
import pl.zzpwjj.restaurant.core.model.dto.DishDto;
import pl.zzpwjj.restaurant.core.model.inputs.AddDishInput;
import pl.zzpwjj.restaurant.core.services.DishesService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/dishes")
@Api(value = "dishes endpoint")
@RestController
public class DishEndpoint {

    private DishesService dishesService;
    private DishesConverter dishesConverter;

    @Autowired
    public DishEndpoint(final DishesService dishesService, final DishesConverter dishesConverter) {
        this.dishesService = dishesService;
        this.dishesConverter = dishesConverter;
    }

    @ApiOperation(value = "Returns all dishes")
    @GetMapping("/getDishes")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<DishDto>> getDishes() {
        List<DishDto> dishDtos = dishesConverter.convertDishes(dishesService.getDishes());
        return new ResponseEntity<>(dishDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns dish")
    @GetMapping("/getDish")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<DishDto> getDish(@RequestParam("id") @NotNull final Long id) {
        DishDto dishDto;
        try {
            dishDto = dishesConverter.convertDish(dishesService.getDish(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dishDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds dish")
    @PostMapping("/addDish")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addDish(@RequestBody @Valid final AddDishInput addDishInput) {
        dishesService.addDish(addDishInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes dish")
    @DeleteMapping("/deleteDish")
    public ResponseEntity<Void> deleteDish(@RequestParam("id") @NotNull final Long id) {
        try {
            dishesService.deleteDish(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits dish")
    @PostMapping("/editDish")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editDish(@RequestBody @Valid final DishDto dishDto) {
        try {
            dishesService.editDish(dishDto);
        } catch (InvalidParametersException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
