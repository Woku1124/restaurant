package pl.zzpwjj.restaurant.core.model.dto;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotNull
    private Long id;

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
