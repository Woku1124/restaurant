package pl.zzpwjj.restaurant.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.model.entities.Rating;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByDish_Id(Long dish);

    List<Rating> findAllByDish_Name(String name);

    List<Rating> findAllByMark(Integer mark);

    List<Rating> findAllByMarkGreaterThan(Integer mark);

    List<Rating> findAllByMarkLessThan(Integer mark);

}
