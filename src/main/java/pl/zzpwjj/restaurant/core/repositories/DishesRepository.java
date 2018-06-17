package pl.zzpwjj.restaurant.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.entities.Dish;

import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dish, Long> {

    Dish findByName(String name);

    List<Dish> findAllByDishType_Name(String name);
    List<Dish> findAllByDishType_Id(Long id);
}
