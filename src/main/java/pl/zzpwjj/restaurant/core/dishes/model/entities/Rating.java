package pl.zzpwjj.restaurant.core.dishes.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "rating")
public class Rating {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish")
    private Dish dish;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer mark;

    @NotNull
    private String comment;
}
