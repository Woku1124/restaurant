package pl.zzpwjj.restaurant.core.dishes.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.ValidatorBase;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishTypeInput;

@Service
public class DishTypeValidator extends ValidatorBase<AddDishTypeInput> {
    @Override
    public void validate(AddDishTypeInput addDishTypeInput) throws InvalidParametersException {
        String errors = "";

        //name
        errors += validateName(addDishTypeInput.getName(), "TypeName");

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}
