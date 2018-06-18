package pl.zzpwjj.restaurant.core.foodOrders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.FoodOrder;

import java.util.List;

@Repository
public interface FoodOrdersRepository extends JpaRepository<FoodOrder, Long> {

    @Query("SELECT fo FROM food_order fo WHERE fo.dateOfRealization = null")
    List<FoodOrder> findAllWhereDateOfRealizationIsNull();

    
}