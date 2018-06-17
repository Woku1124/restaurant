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
public class AddAddressInput {
    @NotNull
    private Long phoneNr;

    @NotNull
    private String email;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private int homeNr;

    private int flatNr;
}
