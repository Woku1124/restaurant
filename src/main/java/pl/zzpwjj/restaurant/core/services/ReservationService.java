package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.DataSourceException;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.entities.Reservation;
import pl.zzpwjj.restaurant.core.model.entities.Table;
import pl.zzpwjj.restaurant.core.model.inputs.AddReservationInput;
import pl.zzpwjj.restaurant.core.repositories.ReservationRepository;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private PersonalDatasService personalDatasService;
    private AddressesService addressesService;
    private TablesService tablesService;
    private EmailSenderService emailSenderService;

    @Autowired
    public ReservationService(final ReservationRepository reservationRepository, final PersonalDatasService personalDatasService, final AddressesService addressesService,
                              final TablesService tablesService, final EmailSenderService emailSenderService){
        this.reservationRepository=reservationRepository;
        this.personalDatasService=personalDatasService;
        this.addressesService=addressesService;
        this.tablesService=tablesService;
        this.emailSenderService=emailSenderService;
    }

    public List<Reservation> getAllReservations()
    {
        return reservationRepository.findAll();
    }

    public Reservation getReservation(final Long id) throws ItemNotFoundException {
        return reservationRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addReservation(AddReservationInput addReservationInput) throws DataSourceException, MessagingException {
        Reservation reservation = new Reservation();
        reservation.setAddressId(addReservationInput.getAddressId());
        reservation.setReservationDateTime(addReservationInput.getReservationDateTime());
        reservation.setClientPersonalDataId(addReservationInput.getClientPersonalDataId());
        List<Table> freeTables = tablesService.getFreeTables();
        if(freeTables.isEmpty()){
            throw new DataSourceException("No free Tables");
        }
        reservationRepository.save(reservation);
        tablesService.reserveTable(reservation,freeTables.get(0));
        emailSenderService.send(reservation.getAddressId().getEmail(),reservation.getReservationDateTime());
    }
    public void editReservation(Reservation reservation) throws InvalidParametersException {
        if (!reservationRepository.existsById(reservation.getId())) {
            throw new InvalidParametersException("Food order with id = " + reservation.getId() + " does not exist");
        }
        reservationRepository.save(reservation);
    }
    public void deleteReservation(final Long id) throws ItemNotFoundException {
        try {
            Reservation reservation = reservationRepository.findById(id).get();
            personalDatasService.deletePersonalData(reservation.getClientPersonalDataId().getId());
            addressesService.deleteAddress(reservation.getAddressId().getId());
            reservationRepository.deleteById(id);

        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException("Reservation with id = " + id + " does not exist", e);
        }
    }

    public void clearReservationsThatWereCompleted()
    {
        List<Reservation> reservationsToBeDeleted = reservationRepository.findAll().stream().
                filter(e -> e.getReservationDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        reservationsToBeDeleted.forEach(e -> {
            try {
                deleteReservation(e.getId());
            } catch (ItemNotFoundException e1) {
                e1.printStackTrace();
            }
        });
    }
}
