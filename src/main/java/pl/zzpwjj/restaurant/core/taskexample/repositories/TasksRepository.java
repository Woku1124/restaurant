package pl.zzpwjj.restaurant.core.taskexample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.zzpwjj.restaurant.core.taskexample.model.entities.Task;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {
}
