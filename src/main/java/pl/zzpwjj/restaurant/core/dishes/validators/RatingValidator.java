package pl.zzpwjj.restaurant.core.dishes.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.ValidatorBase;
import pl.zzpwjj.restaurant.core.dishes.model.input.AddRatingInput;

@Service
public class RatingValidator extends ValidatorBase<AddRatingInput> {
    @Override
    public void validate(AddRatingInput addRatingInput) throws InvalidParametersException {
        String errors = "";

        //dishName
        errors += validateDescription(addRatingInput.getDishName(), "DishName");
        //mark
        errors += validateInteger(addRatingInput.getMark(), "Mark");
        //comment
        errors += validateDescription(addRatingInput.getComment(), "Comment");

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}
