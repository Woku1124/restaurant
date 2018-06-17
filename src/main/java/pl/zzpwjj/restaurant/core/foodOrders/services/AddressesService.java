package pl.zzpwjj.restaurant.core.foodOrders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddAddressInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.AddressDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.Address;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.AddressesRepository;

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
        address.setPhoneNr(addAddressInput.getPhoneNr());
        address.setEmail(addAddressInput.getEmail());
        address.setCity(addAddressInput.getCity());
        address.setStreet(addAddressInput.getStreet());
        address.setHomeNr(addAddressInput.getHomeNr());
        address.setFlatNr(addAddressInput.getFlatNr());

        return addressesRepository.save(address);
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
        address.setPhoneNr(addressDto.getPhone_nr());
        address.setEmail(addressDto.getEmail());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setHomeNr(addressDto.getHomeNr());
        address.setFlatNr(addressDto.getFlatNr());

        addressesRepository.save(address);
    }
}