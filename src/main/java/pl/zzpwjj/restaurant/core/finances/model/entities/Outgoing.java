package pl.zzpwjj.restaurant.core.finances.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "outgoings")
public class Outgoing {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String typeOfOutgoing;

    @NotNull
    private Double valueOfOutgoing;

    private String pesel;

    @NotNull
    private LocalDateTime dateOfOutgoing;

}
