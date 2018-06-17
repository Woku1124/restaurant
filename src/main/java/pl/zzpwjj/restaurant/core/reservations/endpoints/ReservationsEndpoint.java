package pl.zzpwjj.restaurant.core.reservations.endpoints;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.DataSourceException;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.reservations.converters.ReservationConverter;
import pl.zzpwjj.restaurant.core.reservations.model.dto.ReservationDto;
import pl.zzpwjj.restaurant.core.reservations.model.inputs.AddReservationInput;
import pl.zzpwjj.restaurant.core.reservations.services.ReservationService;
import pl.zzpwjj.restaurant.core.reservations.validators.ReservationsValidator;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ReservationsEndpoint {

    private ReservationService reservationService;
    private ReservationConverter reservationConverter;
    private ReservationsValidator reservationsValidator;

    @Autowired
    public ReservationsEndpoint(final ReservationService reservationService, final ReservationConverter reservationConverter, final ReservationsValidator reservationsValidator)
    {
        this.reservationConverter=reservationConverter;
        this.reservationService=reservationService;
        this.reservationsValidator=reservationsValidator;
    }
    @ApiOperation(value = "Returns all reservations")
    @GetMapping("/getReservations")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<ReservationDto>> getReservations()
    {
        return new ResponseEntity<>(reservationConverter.convertReservations(reservationService.getAllReservations()), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns reservation by id")
    @GetMapping("/getReservation")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ReservationDto> getReservation(@RequestParam("id") @NotNull final Long id)
    {
        try{
            return new ResponseEntity<>(reservationConverter.convertReservation(reservationService.getReservation(id)),HttpStatus.OK);
        }
        catch (ItemNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @ApiOperation(value = "Reserves a table")
    @PostMapping("/reserve")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> reserveTable(@RequestBody @Valid final AddReservationInput addReservationInput)
    {
        try {
            reservationsValidator.validateAddReservationInput(addReservationInput);
        } catch (InvalidParametersException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
        try {
            reservationService.addReservation(addReservationInput);
        } catch (DataSourceException | MessagingException | MailSendException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @ApiOperation(value = "Clears outdated reservations")
    @GetMapping("/clearOutdatedReservations")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> clearReservations()
    {
        reservationService.clearReservationsThatWereCompleted();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edit reservation")
    @PostMapping("/editReservation")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> editReservation(@RequestBody @Valid final ReservationDto reservationDto)
    {
        try {
            reservationService.editReservation(reservationDto);
        } catch (InvalidParametersException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Removes reservation")
    @DeleteMapping("/deleteReservation")
    public ResponseEntity<String> deleteReservation(@RequestParam("id") @NotNull final Long id)
    {
        try {
            reservationService.deleteReservation(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>("Reservation not found",HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
