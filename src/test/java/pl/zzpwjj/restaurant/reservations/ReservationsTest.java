package pl.zzpwjj.restaurant.reservations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.DataSourceException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.Address;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.PersonalData;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddAddressInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddPersonalDataInput;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.AddressesRepository;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.PersonalDatasRepository;
import pl.zzpwjj.restaurant.core.foodOrders.services.AddressesService;
import pl.zzpwjj.restaurant.core.foodOrders.services.PersonalDatasService;
import pl.zzpwjj.restaurant.core.reservations.converters.ReservationConverter;
import pl.zzpwjj.restaurant.core.reservations.model.dto.ReservationDto;
import pl.zzpwjj.restaurant.core.reservations.model.entities.Reservation;
import pl.zzpwjj.restaurant.core.reservations.model.inputs.AddReservationInput;
import pl.zzpwjj.restaurant.core.reservations.repositories.ReservationRepository;
import pl.zzpwjj.restaurant.core.reservations.services.ReservationService;
import pl.zzpwjj.restaurant.core.services.EmailSenderService;
import pl.zzpwjj.restaurant.core.tables.model.entities.Table;
import pl.zzpwjj.restaurant.core.tables.model.inputs.AddTableInput;
import pl.zzpwjj.restaurant.core.tables.repositories.TablesRepository;
import pl.zzpwjj.restaurant.core.tables.services.TablesService;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReservationsTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private TablesRepository tablesRepository;
    @Mock
    private AddressesRepository addressesRepository;
    @Mock
    private PersonalDatasRepository personalDatasRepository;
    @Mock
    private EmailSenderService emailSenderService;
    @Mock
    private ReservationConverter reservationConverter;

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private TablesService tablesService;
    @Mock
    private AddressesService addressesService;
    @Mock
    private PersonalDatasService personalDatasService;


    @Test
    public void addReservationPositive() throws DataSourceException, MessagingException {
        // given
        AddReservationInput reservationInput = new AddReservationInput();
        reservationInput.setAddressId(null);
        reservationInput.setClientPersonalDataId(null);
        reservationInput.setReservationDateTime(LocalDateTime.now().plusDays(1));
        Table table = new Table();
        table.setId(new Long(12));
        table.setReservationId(null);
        Address address1 = new Address();
        address1.setId(new Long(10));
        address1.setEmail("piorok4@wp.pl");
        address1.setCity("s");
        address1.setStreet("ss");
        address1.setPhoneNr(new Long(999999999));
        address1.setFlatNr(12);
        address1.setHomeNr(12);
        PersonalData personalData = new PersonalData();
        personalData.setId(new Long(11));
        personalData.setName("go");
        personalData.setSurname("go");
        Reservation reservation = new Reservation();
        reservation.setId(new Long(13));
        reservation.setAddressId(address1);
        reservation.setClientPersonalDataId(personalData);
        reservation.setReservationDateTime(reservationInput.getReservationDateTime());
        List<Table> freeTables = new ArrayList<>();
        freeTables.add(table);

        // when
        Mockito.when(tablesRepository.save(Mockito.any(Table.class))).thenReturn(table);
        Mockito.when(tablesService.getFreeTables()).thenReturn(freeTables);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);
        Reservation createdReservation = reservationService.addReservation(reservationInput);

        // then
        Assert.assertNotNull(createdReservation);
        Assert.assertEquals(reservation.getClientPersonalDataId().getName(), createdReservation.getClientPersonalDataId().getName());
        Assert.assertEquals(reservation.getClientPersonalDataId().getSurname(), createdReservation.getClientPersonalDataId().getSurname());
        Assert.assertEquals(reservation.getReservationDateTime(), createdReservation.getReservationDateTime());
        Assert.assertEquals(reservation.getAddressId().getCity(),createdReservation.getAddressId().getCity());
        Assert.assertEquals(reservation.getAddressId().getEmail(),createdReservation.getAddressId().getEmail());
        Assert.assertEquals(reservation.getAddressId().getFlatNr(),createdReservation.getAddressId().getFlatNr());
        Assert.assertEquals(reservation.getAddressId().getHomeNr(),createdReservation.getAddressId().getHomeNr());
        Assert.assertEquals(reservation.getAddressId().getPhoneNr(),createdReservation.getAddressId().getPhoneNr());
        Assert.assertEquals(reservation.getAddressId().getStreet(),createdReservation.getAddressId().getStreet());
    }

    @Test
    public void editReservation() throws Exception {
        // given
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setAddressId(null);
        reservationDto.setClientPersonalDataId(null);
        reservationDto.setReservationDateTime(LocalDateTime.now().plusDays(1));
        reservationDto.setId(1L);
        Table table = new Table();
        table.setId(new Long(12));
        table.setReservationId(null);
        Address address1 = new Address();
        address1.setId(new Long(10));
        address1.setEmail("piorok4@wp.pl");
        address1.setCity("s");
        address1.setStreet("ss");
        address1.setPhoneNr(new Long(999999999));
        address1.setFlatNr(12);
        address1.setHomeNr(12);
        PersonalData personalData = new PersonalData();
        personalData.setId(new Long(11));
        personalData.setName("go");
        personalData.setSurname("go");
        Reservation reservation = new Reservation();
        reservation.setId(new Long(13));
        reservation.setAddressId(address1);
        reservation.setClientPersonalDataId(personalData);
        reservation.setReservationDateTime(reservationDto.getReservationDateTime());
        List<Table> freeTables = new ArrayList<>();
        freeTables.add(table);

        // when
        Mockito.when(reservationRepository.existsById(reservationDto.getId())).thenReturn(true);
        Mockito.when(tablesRepository.save(Mockito.any(Table.class))).thenReturn(table);
        Mockito.when(tablesService.getFreeTables()).thenReturn(freeTables);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);
        Mockito.when(reservationConverter.convertToReservationFromDto(Mockito.any())).thenReturn(reservation);
        Reservation updatedReservation = reservationService.editReservation(reservationDto);

        // then
        Assert.assertNotNull(updatedReservation);
        Assert.assertEquals(reservation.getClientPersonalDataId().getName(), updatedReservation.getClientPersonalDataId().getName());
        Assert.assertEquals(reservation.getClientPersonalDataId().getSurname(), updatedReservation.getClientPersonalDataId().getSurname());
        Assert.assertEquals(reservation.getReservationDateTime(), updatedReservation.getReservationDateTime());
        Assert.assertEquals(reservation.getAddressId().getCity(),updatedReservation.getAddressId().getCity());
        Assert.assertEquals(reservation.getAddressId().getEmail(),updatedReservation.getAddressId().getEmail());
        Assert.assertEquals(reservation.getAddressId().getFlatNr(),updatedReservation.getAddressId().getFlatNr());
        Assert.assertEquals(reservation.getAddressId().getHomeNr(),updatedReservation.getAddressId().getHomeNr());
        Assert.assertEquals(reservation.getAddressId().getPhoneNr(),updatedReservation.getAddressId().getPhoneNr());
        Assert.assertEquals(reservation.getAddressId().getStreet(),updatedReservation.getAddressId().getStreet());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteReservationFAILTHROW() throws Exception {
        // given
        Long id = 1L;

        // when
        Mockito.when(reservationRepository.existsById(id)).thenReturn(false);
        reservationService.deleteReservation(id);

        // then
        Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(id);
    }


}