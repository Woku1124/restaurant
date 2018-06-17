package pl.zzpwjj.restaurant.core.foodOrders.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "food_order")
public class FoodOrder {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "personal_data")
    private PersonalData personalData;

    @OneToOne
    @JoinColumn(name = "address")
    private Address address;

    @NotNull
    private Double full_price;

    @NotNull
    private LocalDate dateOfOrder;

    private LocalDate dateOfRealization;

}