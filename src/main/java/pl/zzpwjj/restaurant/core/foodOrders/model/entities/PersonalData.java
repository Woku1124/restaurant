package pl.zzpwjj.restaurant.core.foodOrders.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "personal_data")
public class PersonalData {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min=1, message="Name should have atleast 2 characters")
    @Size(max=15, message="Name shouldn't have more than 15 characters")
    private String name;

    @NotNull
    @Size(min=1, message="Surname should have atleast 2 characters")
    @Size(max=20, message="Name shouldn't have more than 20 characters")
    private String surname;

}