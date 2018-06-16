package pl.zzpwjj.restaurant.core.converters;

import pl.zzpwjj.restaurant.core.model.dto.ReservationDto;
import pl.zzpwjj.restaurant.core.model.entities.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationConverter {

    public ReservationDto convertReservation(final Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setClientPersonalDataId(reservation.getClientPersonalDataId());
        reservationDto.setAddressId(reservation.getAddressId());
        reservationDto.setReservationDateTime(reservation.getReservationDateTime());
        return reservationDto;
    }

    public List<ReservationDto> convertFoodOrders(final List<Reservation> foodOrders) {
        return foodOrders.stream().map(this::convertReservation).collect(Collectors.toList());
    }
}
