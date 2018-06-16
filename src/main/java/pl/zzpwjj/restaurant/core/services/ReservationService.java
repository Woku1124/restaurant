package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.entities.Reservation;
import pl.zzpwjj.restaurant.core.model.inputs.AddReservationInput;
import pl.zzpwjj.restaurant.core.repositories.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(final ReservationRepository reservationRepository){
        this.reservationRepository=reservationRepository;

    }

    public List<Reservation> getAllReservations()
    {
        return reservationRepository.findAll();
    }

    public Reservation getReservation(final Long id) throws ItemNotFoundException {
        return reservationRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addReservation(AddReservationInput addReservationInput){
        Reservation reservation = new Reservation();
        reservation.setAddressId(addReservationInput.getAddressId());
        reservation.setReservationDateTime(addReservationInput.getReservationDateTime());
        reservation.setClientPersonalDataId(addReservationInput.getClientPersonalDataId());

        reservationRepository.save(reservation);
    }


}
