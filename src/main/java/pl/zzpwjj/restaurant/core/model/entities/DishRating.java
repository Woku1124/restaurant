package pl.zzpwjj.restaurant.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "dish_rating")
public class DishRating {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rating")
    private Rating rating;

    @ManyToOne
    @JoinColumn(name = "dish")
    private Dish dish_id;
}
