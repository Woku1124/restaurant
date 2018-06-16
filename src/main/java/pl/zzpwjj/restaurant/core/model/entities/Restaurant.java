package pl.zzpwjj.restaurant.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "address")
    private Address address_id;

}