package pl.zzpwjj.restaurant.core.foodOrders.validators;

import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.ValidatorBase;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddFoodOrderInput;

@Service
public class FoodOrderValidator extends ValidatorBase<AddFoodOrderInput> {
    @Override
    public void validate(AddFoodOrderInput addFoodOrderInput) throws InvalidParametersException {
        //TODO dishes validator -> waiting for Pioter dishes validator to use him
    }
}
