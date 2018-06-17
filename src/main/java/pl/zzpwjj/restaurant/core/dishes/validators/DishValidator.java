package pl.zzpwjj.restaurant.core.dishes.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.ValidatorBase;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddDishInput;

@Service
public class DishValidator extends ValidatorBase<AddDishInput> {
    @Override
    public void validate(AddDishInput addDishInput) throws InvalidParametersException {
        String errors = "";

        //dishTypeInput

        //price
        //Double price = addDishInput.getPrice();

        //name
        errors += validateDescription(addDishInput.getName(), "DishName");
        //description
        errors += validateDescription(addDishInput.getDescription(), "Description");

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}
