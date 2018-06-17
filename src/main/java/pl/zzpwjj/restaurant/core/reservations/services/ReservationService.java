package pl.zzpwjj.restaurant.core.reservations.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.zzpwjj.restaurant.common.exceptions.DataSourceException;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.reservations.converters.ReservationConverter;
import pl.zzpwjj.restaurant.core.reservations.model.dto.ReservationDto;
import pl.zzpwjj.restaurant.core.reservations.model.entities.Reservation;
import pl.zzpwjj.restaurant.core.tables.model.entities.Table;
import pl.zzpwjj.restaurant.core.reservations.model.inputs.AddReservationInput;
import pl.zzpwjj.restaurant.core.reservations.repositories.ReservationRepository;
import pl.zzpwjj.restaurant.core.services.AddressesService;
import pl.zzpwjj.restaurant.core.services.EmailSenderService;
import pl.zzpwjj.restaurant.core.services.PersonalDatasService;
import pl.zzpwjj.restaurant.core.tables.services.TablesService;

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
    private ReservationConverter reservationConverter;
    @Autowired
    public ReservationService(final ReservationRepository reservationRepository, final PersonalDatasService personalDatasService, final AddressesService addressesService,
                              final TablesService tablesService, final EmailSenderService emailSenderService, final ReservationConverter reservationConverter){
        this.reservationRepository=reservationRepository;
        this.personalDatasService=personalDatasService;
        this.addressesService=addressesService;
        this.tablesService=tablesService;
        this.emailSenderService=emailSenderService;
        this.reservationConverter=reservationConverter;
    }

    public List<Reservation> getAllReservations()
    {
        return reservationRepository.findAll();
    }

    public Reservation getReservation(final Long id) throws ItemNotFoundException {
        return reservationRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addReservation(AddReservationInput addReservationInput) throws DataSourceException, MessagingException , MailSendException {
        Reservation reservation = new Reservation();
        reservation.setAddressId(addressesService.addAddress(addReservationInput.getAddressId()));
        reservation.setReservationDateTime(addReservationInput.getReservationDateTime());
        reservation.setClientPersonalDataId(personalDatasService.addPersonalData(addReservationInput.getClientPersonalDataId()));
        List<Table> freeTables = tablesService.getFreeTables();
        if(freeTables.isEmpty()){
            throw new DataSourceException("No free Tables");
        }
        reservationRepository.save(reservation);
        tablesService.reserveTable(reservation,freeTables.get(0));
        emailSenderService.send(reservation.getAddressId().getEmail(),reservation.getReservationDateTime());
    }
    public void editReservation(ReservationDto reservationDto) throws InvalidParametersException {
        if (!reservationRepository.existsById(reservationDto.getId())) {
            throw new InvalidParametersException("Food order with id = " + reservationDto.getId() + " does not exist");
        }
        reservationRepository.save(reservationConverter.convertToReservationFromDto(reservationRepository.findById(reservationDto.getId())));
    }
    public void deleteReservation(final Long id) throws ItemNotFoundException {
        try {
            Reservation reservation = reservationRepository.findById(id).get();
            tablesService.getAllTables().stream().filter(e -> e.getReservationId()==reservation).findAny().get().setReservationId(null);
            reservationRepository.deleteById(id);
            personalDatasService.deletePersonalData(reservation.getClientPersonalDataId().getId());
            addressesService.deleteAddress(reservation.getAddressId().getId());


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
