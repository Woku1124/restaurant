package pl.zzpwjj.restaurant.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.model.entities.FoodOrder;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodOrdersRepository extends JpaRepository<FoodOrder, Long> {

    @Query("SELECT fo FROM food_order fo WHERE fo.dateOfRealization = null")
    List<FoodOrder> findAllWhereDateOfRealizationIsNull();
}