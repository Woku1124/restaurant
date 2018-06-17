package pl.zzpwjj.restaurant.core.reservations.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.Address;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.PersonalData;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    @NotNull
    private Long id;

    @NotNull
    private PersonalData clientPersonalDataId;

    @NotNull
    private Address addressId;

    @NotNull
    private LocalDateTime reservationDateTime;

}
