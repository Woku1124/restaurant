package pl.zzpwjj.restaurant.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.inputs.AddAddressInput;
import pl.zzpwjj.restaurant.core.model.dto.AddressDto;
import pl.zzpwjj.restaurant.core.model.entities.Address;
import pl.zzpwjj.restaurant.core.repositories.AddressesRepository;

@Service
public class AddressesService {

    private AddressesRepository addressesRepository;

    @Autowired
    public AddressesService(final AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    public List<Address> getAddresses() {
        return addressesRepository.findAll();
    }

    public Address getAddress(final Long id) throws ItemNotFoundException {
        return addressesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Address addAddress(final AddAddressInput addAddressInput) {
        Address address = new Address();
        address.setPhone_nr(addAddressInput.getPhone_nr());
        address.setEmail(addAddressInput.getEmail());
        address.setCity(addAddressInput.getCity());
        address.setStreet(addAddressInput.getStreet());
        address.setHome_nr(addAddressInput.getHome_nr());
        address.setFlat_nr(addAddressInput.getFlat_nr());

        addressesRepository.save(address);
        return address;
    }

    public void deleteAddress(final Long id) throws ItemNotFoundException {
        try {
            addressesRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Address with id = " + id + " does not exist", e);
        }
    }

    public void editAddress(final AddressDto addressDto) throws InvalidParametersException {
        if (!addressesRepository.existsById(addressDto.getId())) {
            throw new InvalidParametersException("Address with id = " + addressDto.getId() + " does not exist");
        }

        Address address = new Address();
        address.setId(addressDto.getId());
        address.setPhone_nr(addressDto.getPhone_nr());
        address.setEmail(addressDto.getEmail());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setHome_nr(addressDto.getHome_nr());
        address.setFlat_nr(addressDto.getFlat_nr());

        addressesRepository.save(address);
    }
}