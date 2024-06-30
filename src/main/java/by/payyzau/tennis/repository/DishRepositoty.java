package by.payyzau.tennis.repository;

import by.payyzau.tennis.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepositoty extends JpaRepository<Dish, Long> {
}
