package pl.zzpwjj.restaurant.core.foodOrders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.Address;

@Repository
public interface AddressesRepository extends JpaRepository<Address, Long> {
}

