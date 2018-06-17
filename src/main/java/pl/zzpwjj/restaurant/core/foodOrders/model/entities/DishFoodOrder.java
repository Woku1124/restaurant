package pl.zzpwjj.restaurant.core.foodOrders.model.entities;

import lombok.Getter;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.dishes.model.entities.Dish;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "dish_food_order")
public class DishFoodOrder {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_order")
    private FoodOrder foodOrder;

    @ManyToOne
    @JoinColumn(name = "dishName")
    private Dish dish;

}