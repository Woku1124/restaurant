package pl.zzpwjj.restaurant.core.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.model.dto.AddressDto;
import pl.zzpwjj.restaurant.core.model.entities.Address;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressesConverter {
    public AddressDto convertAddress(final Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setPhone_nr(address.getPhone_nr());
        addressDto.setEmail(address.getEmail());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setHome_nr(address.getHome_nr());
        addressDto.setFlat_nr(address.getFlat_nr());
        return addressDto;
    }

    public List<AddressDto> convertAddresses(final List<Address> addresses) {
        return addresses.stream().map(this::convertAddress).collect(Collectors.toList());
    }
}
