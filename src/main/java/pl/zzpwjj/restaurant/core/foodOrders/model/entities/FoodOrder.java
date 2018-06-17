package pl.zzpwjj.restaurant.core.foodOrders.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

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
    @Range(min = 0, message = "Please select positive numbers only")
    private Double full_price;

    @NotNull
    private LocalDate dateOfOrder;

    private LocalDate dateOfRealization;

}