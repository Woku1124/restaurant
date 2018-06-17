package pl.zzpwjj.restaurant.core.foodOrders.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(min=1, message="City should have atleast 2 characters")
    @Size(max=20, message="City shouldn't have more than 20 characters")
    private String email;

    @NotNull
    @Size(min=1, message="City should have atleast 2 characters")
    @Size(max=20, message="City shouldn't have more than 20 characters")
    private String city;

    @NotNull
    @Size(min=1, message="Street should have atleast 2 characters")
    @Size(max=20, message="Street shouldn't have more than 20 characters")
    private String street;

    @NotNull
    private Integer homeNr;

    private Integer flatNr;


}