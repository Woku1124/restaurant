package pl.zzpwjj.restaurant.core.dishes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.dishes.model.entities.DishType;

@Repository
public interface DishTypesRepository extends JpaRepository<DishType, Long> {

    DishType findByName(String name);
}

