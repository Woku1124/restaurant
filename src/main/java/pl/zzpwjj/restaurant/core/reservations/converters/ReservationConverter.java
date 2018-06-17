package pl.zzpwjj.restaurant.core.reservations.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.reservations.model.dto.ReservationDto;
import pl.zzpwjj.restaurant.core.reservations.model.entities.Reservation;

import java.util.List;
import java.util.Optional;
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

    public Reservation convertToReservationFromDto(final Optional<Reservation> reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.get().getId());
        reservation.setClientPersonalDataId(reservationDto.get().getClientPersonalDataId());
        reservation.setReservationDateTime(reservationDto.get().getReservationDateTime());
        reservation.setAddressId(reservationDto.get().getAddressId());
        return reservation;
    }

    public List<ReservationDto> convertReservations(final List<Reservation> reservations) {
        return reservations.stream().map(this::convertReservation).collect(Collectors.toList());
    }
}
