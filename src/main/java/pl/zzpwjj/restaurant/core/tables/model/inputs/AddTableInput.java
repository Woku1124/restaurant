package pl.zzpwjj.restaurant.core.tables.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.reservations.model.entities.Reservation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTableInput {

    private Reservation reservationId;
}
