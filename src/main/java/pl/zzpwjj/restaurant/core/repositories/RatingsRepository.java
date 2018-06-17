package pl.zzpwjj.restaurant.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.model.entities.Rating;

@Repository
public interface RatingsRepository extends JpaRepository<Rating, Long> {
}
