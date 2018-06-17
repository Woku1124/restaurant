package pl.zzpwjj.restaurant.core.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddAddressInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddPersonalDataInput;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddReservationInput{

        @NotNull
        private LocalDateTime reservationDateTime;

        @NotNull
        private AddPersonalDataInput clientPersonalDataId;

        @NotNull
        private AddAddressInput addressId;
}
