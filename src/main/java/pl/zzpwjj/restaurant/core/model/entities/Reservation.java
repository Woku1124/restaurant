package pl.zzpwjj.restaurant.core.model.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDateTime reservationDateTime;

    @OneToOne
    @JoinColumn(name = "personal_data")
    private PersonalData clientPersonalDataId;

    @OneToOne
    @JoinColumn(name = "address")
    private Address addressId;
}
