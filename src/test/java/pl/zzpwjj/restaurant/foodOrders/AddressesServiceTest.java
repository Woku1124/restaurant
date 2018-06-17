package pl.zzpwjj.restaurant.foodOrders;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.AddressDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.Address;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddAddressInput;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.AddressesRepository;
import pl.zzpwjj.restaurant.core.foodOrders.services.AddressesService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddressesServiceTest {
    @InjectMocks
    private AddressesService addressesService;

    @Mock
    private AddressesRepository addressesRepository;

    @Test
    public void shouldCreateAddress() {
        // given
        AddAddressInput address = new AddAddressInput();
        address.setCity("Rawicz");
        address.setEmail("203946@edu.p.lodz.pl");
        address.setStreet("Piotrkowska");
        address.setHomeNr(2);
        address.setPhoneNr(123456789l);

        // when
        Mockito.when(addressesRepository.save(Mockito.any(Address.class))).then(AdditionalAnswers.returnsFirstArg());
        Address createdAddress = addressesService.addAddress(address);

        // then
        Assert.assertEquals(address.getCity(), createdAddress.getCity());
        Assert.assertEquals(address.getEmail(), createdAddress.getEmail());
        Assert.assertEquals(address.getStreet(), createdAddress.getStreet());
        Assert.assertEquals(address.getHomeNr(), createdAddress.getHomeNr());
        Assert.assertEquals(address.getFlatNr(), createdAddress.getFlatNr());
        Assert.assertEquals(address.getFlatNr(), createdAddress.getFlatNr());
    }

    @Test
    public void shouldDeleteAddress() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(addressesRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(addressesRepository).deleteById(id);
        addressesService.deleteAddress(id);

        // then
        Mockito.verify(addressesRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeleteAddressButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(addressesRepository.existsById(id)).thenReturn(false);
        addressesService.deleteAddress(id);

        // then throw an exception
    }

    @Test
    public void shouldUpdateAddress() throws ItemNotFoundException, InvalidParametersException {
        // given
        AddressDto address = new AddressDto();
        address.setId(1l);
        address.setCity("Rawicz");
        address.setEmail("203946@edu.p.lodz.pl");
        address.setStreet("Piotrkowska");
        address.setHomeNr(2);
        address.setPhone_nr(123456789l);

        // when
        Mockito.when(addressesRepository.existsById(address.getId())).thenReturn(true);
        Mockito.when(addressesRepository.save(Mockito.any(Address.class))).then(AdditionalAnswers.returnsFirstArg());
        Address updatedAddress = addressesService.editAddress(address);

        // then
        Assert.assertEquals(address.getCity(), updatedAddress.getCity());
        Assert.assertEquals(address.getEmail(), updatedAddress.getEmail());
        Assert.assertEquals(address.getStreet(), updatedAddress.getStreet());
        Assert.assertEquals(address.getHomeNr(), updatedAddress.getHomeNr());
        Assert.assertEquals(address.getFlatNr(), updatedAddress.getFlatNr());
        Assert.assertEquals(address.getFlatNr(), updatedAddress.getFlatNr());
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotUpdateAddressButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        AddressDto address = new AddressDto();
        address.setId(1l);
        address.setCity("Rawicz");
        address.setEmail("203946@edu.p.lodz.pl");
        address.setStreet("Piotrkowska");
        address.setHomeNr(2);
        address.setPhone_nr(123456789l);

        // when
        Mockito.when(addressesRepository.existsById(address.getId())).thenReturn(false);
        Address updatedAddress = addressesService.editAddress(address);

        // then throw an exception
    }

}