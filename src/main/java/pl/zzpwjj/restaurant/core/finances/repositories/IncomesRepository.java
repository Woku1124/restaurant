package pl.zzpwjj.restaurant.core.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.finances.model.entities.Income;

@Repository
public interface IncomesRepository extends JpaRepository<Income, Long> {
}
