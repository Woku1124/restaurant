package pl.zzpwjj.restaurant.core.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.converters.RatingsConverter;
import pl.zzpwjj.restaurant.core.model.dto.RatingDto;
import pl.zzpwjj.restaurant.core.model.inputs.AddRatingInput;
import pl.zzpwjj.restaurant.core.services.RatingsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/ratings")
@Api(value = "Ratings endpoint")
@RestController
public class RatingsEndpoint {

    private RatingsService ratingsService;
    private RatingsConverter ratingsConverter;

    @Autowired
    public RatingsEndpoint(final RatingsService ratingsService, final RatingsConverter ratingsConverter) {
        this.ratingsService = ratingsService;
        this.ratingsConverter = ratingsConverter;
    }

    @ApiOperation(value = "Returns all ratings")
    @GetMapping("/getRatings")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<RatingDto>> getRatings() {
        List<RatingDto> ratingDtos = ratingsConverter.convertRatings(ratingsService.getRatings());
        return new ResponseEntity<>(ratingDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all ratings with dish id equals to")
    @GetMapping("/getRatingswithDishIdEqualsTo")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<RatingDto>> getRatingswithDishIdEqualsTo(@RequestParam("dishId") @NotNull final Long Id) {
        List<RatingDto> ratingDtos = ratingsConverter.convertRatings(ratingsService.getRatingsWithDishIdEqualsTo(Id));
        return new ResponseEntity<>(ratingDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns rating")
    @GetMapping("/getRating")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<RatingDto> getRating(@RequestParam("id") @NotNull final Long id) {
        RatingDto ratingDto;
        try {
            ratingDto = ratingsConverter.convertRating(ratingsService.getRating(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ratingDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds rating")
    @PostMapping("/addRating")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addRating(@RequestBody @Valid final AddRatingInput addRatingInput) {
        ratingsService.addRating(addRatingInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes rating")
    @DeleteMapping("/deleteRating")
    public ResponseEntity<Void> deleteRating(@RequestParam("id") @NotNull final Long id) {
        try {
            ratingsService.deleteRating(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits rating")
    @PostMapping("/editRating")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editRating(@RequestBody @Valid final RatingDto ratingDto) {
        try {
            ratingsService.editRating(ratingDto);
        } catch (InvalidParametersException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
