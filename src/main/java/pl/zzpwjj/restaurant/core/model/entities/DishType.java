package pl.zzpwjj.restaurant.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "dish_type")
public class DishType {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String Name;

}