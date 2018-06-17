package pl.zzpwjj.restaurant.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.model.entities.FoodOrder;
import pl.zzpwjj.restaurant.core.model.entities.Rating;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Rating, Long> {

   @Query("SELECT r FROM rating r WHERE r.dish = :dishId")
    List<Rating> findAllWhereDishIdIsEqualTo(@Param("dishId") Long id);

}
