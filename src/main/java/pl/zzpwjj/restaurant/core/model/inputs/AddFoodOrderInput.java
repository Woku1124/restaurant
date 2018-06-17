package pl.zzpwjj.restaurant.core.model.inputs;
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
    private AddPersonalDataInput personal_data;

    @NotNull
    private AddAddressInput address;

    @NotNull
    private List<String> dish_names;


}
