package pl.zzpwjj.restaurant.core.reservations.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.reservations.model.inputs.AddReservationInput;

import java.time.LocalDateTime;

@Service
public class ReservationsValidator {
    public void validateAddReservationInput(final AddReservationInput reservationDto) throws InvalidParametersException {
        String errors = "";
        if(reservationDto.getReservationDateTime().isBefore(LocalDateTime.now()))
        {
            errors+="Reservation can not be made due to date being in the past";
        }
        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}
