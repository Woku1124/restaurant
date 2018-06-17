package pl.zzpwjj.restaurant.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "address")
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long phoneNr;

    @NotNull
    private String email;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private int homeNr;

    private int flatNr;


}