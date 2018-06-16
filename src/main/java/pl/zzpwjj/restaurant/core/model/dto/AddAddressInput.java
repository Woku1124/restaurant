package pl.zzpwjj.restaurant.core.model.dto;

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
    private Long phone_nr;

    @NotNull
    private String email;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private int home_nr;

    private int flat_nr;
}
