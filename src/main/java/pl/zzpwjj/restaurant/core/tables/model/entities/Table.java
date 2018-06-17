package pl.zzpwjj.restaurant.core.tables.model.entities;

import lombok.Getter;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.reservations.model.entities.Reservation;

import javax.persistence.*;


@Getter
@Setter
@Entity(name = "tables")
public class Table {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Reservation reservationId;
}
