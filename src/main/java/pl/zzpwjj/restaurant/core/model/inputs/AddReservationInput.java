package pl.zzpwjj.restaurant.core.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.model.entities.Address;
import pl.zzpwjj.restaurant.core.model.entities.PersonalData;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddReservationInput{
        @NotNull
        private Long id;

        @NotNull
        private LocalDateTime reservationDateTime;

        @NotNull
        private PersonalData clientPersonalDataId;

        @NotNull
        private Address addressId;
}
