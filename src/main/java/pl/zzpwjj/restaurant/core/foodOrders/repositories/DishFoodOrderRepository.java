package pl.zzpwjj.restaurant.core.foodOrders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.DishFoodOrder;

import java.util.List;

@Repository
public interface DishFoodOrderRepository extends JpaRepository<DishFoodOrder, Long> {

    void deleteAllById(Long id);

    @Query("SELECT f.dish FROM dish_food_order f GROUP BY f.dish ORDER BY Count(f.dish) DESC ")
    List<Long> findMostOrderedDishId();

}
