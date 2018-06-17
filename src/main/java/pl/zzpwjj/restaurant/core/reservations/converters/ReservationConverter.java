package pl.zzpwjj.restaurant.core.reservations.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.reservations.model.dto.ReservationDto;
import pl.zzpwjj.restaurant.core.reservations.model.entities.Reservation;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationConverter {

    public ReservationDto convertReservation(final Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setClientPersonalDataId(reservation.getClientPersonalDataId());
        reservationDto.setAddressId(reservation.getAddressId());
        reservationDto.setReservationDateTime(reservation.getReservationDateTime());
        return reservationDto;
    }

    public Reservation convertToReservationFromDto(final ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setClientPersonalDataId(reservationDto.getClientPersonalDataId());
        reservation.setReservationDateTime(reservationDto.getReservationDateTime());
        reservation.setAddressId(reservationDto.getAddressId());
        return reservation;
    }

    public List<ReservationDto> convertReservations(final List<Reservation> reservations) {
        return reservations.stream().map(this::convertReservation).collect(Collectors.toList());
    }
}
