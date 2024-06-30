package by.payyzau.tennis.service;

import by.payyzau.tennis.entity.Dish;
import by.payyzau.tennis.repository.DishRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepositoty dishRepositoty;

    public List<Dish> allDish () {
        return dishRepositoty.findAll();
    }
    public Dish saveDish(Dish dish) {
        return dishRepositoty.save(dish);
    }
}
