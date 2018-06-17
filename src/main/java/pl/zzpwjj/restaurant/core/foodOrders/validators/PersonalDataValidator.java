package pl.zzpwjj.restaurant.core.foodOrders.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.ValidatorBase;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddPersonalDataInput;

@Service
public class PersonalDataValidator extends ValidatorBase<AddPersonalDataInput> {
    @Override
    public void validate(final AddPersonalDataInput personalDataInput) throws InvalidParametersException {
        String errors = "";

        //name
        errors += validateName(personalDataInput.getName(), "Name");
        //surname
        errors += validateName(personalDataInput.getSurname(), "Surname");

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}
