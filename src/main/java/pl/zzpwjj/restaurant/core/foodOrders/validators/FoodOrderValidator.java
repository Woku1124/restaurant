package pl.zzpwjj.restaurant.core.foodOrders.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.ValidatorBase;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddAddressInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddFoodOrderInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddPersonalDataInput;

@Service
public class FoodOrderValidator extends ValidatorBase<AddFoodOrderInput> {

    @Override
    public void validate(AddFoodOrderInput addFoodOrderInput) throws InvalidParametersException {
        String errors = "";
        errors += validatePersonalData(addFoodOrderInput.getPersonalData());
        errors += validateAddress(addFoodOrderInput.getAddress());
        //TODO dishes validator -> waiting for Pioter dishes validator to use him

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }

    private String validateAddress(AddAddressInput addressInput) {
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

        return errors;
    }

    private String validatePersonalData(final AddPersonalDataInput personalDataInput) {
        String errors = "";

        //name
        errors += validateName(personalDataInput.getName(), "Name");
        //surname
        errors += validateName(personalDataInput.getSurname(), "Surname");

        return errors;
    }
}
