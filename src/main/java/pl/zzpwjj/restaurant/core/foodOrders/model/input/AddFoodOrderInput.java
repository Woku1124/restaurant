package pl.zzpwjj.restaurant.core.foodOrders.model.input;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddFoodOrderInput {
    @NotNull
    private AddPersonalDataInput personalData;

    @NotNull
    private AddAddressInput address;

    @NotNull
    private List<String> dishNames;


}
