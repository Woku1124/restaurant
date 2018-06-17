package pl.zzpwjj.restaurant.core.foodOrders.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.ValidatorBase;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddAddressInput;

@Service
public class AddressValidator extends ValidatorBase<AddAddressInput> {


    @Override
    public void validate(AddAddressInput addressInput) throws InvalidParametersException {
        String errors = "";

        //email
        errors += validateEmailAdress(addressInput.getEmail());
        //phoneNumber
        errors += validatePhoneNumber(addressInput.getPhoneNr());
        //city
        errors += validateName(addressInput.getCity(), "City");
        //street
        errors += validateName(addressInput.getStreet(), "Street");
        //homeNr
        errors += validateInteger(addressInput.getHomeNr(), "Home number");
        //flatNr
        errors += validateInteger(addressInput.getFlatNr(), "Flat number");

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}
