package pl.zzpwjj.restaurant.core.foodOrders.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPersonalDataInput {
    @NotNull
    private String name;

    @NotNull
    private String surname;
}
