package pl.zzpwjj.restaurant.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity(name = "tables")
public class Table {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservations")
    private Reservation reservationId;
}
