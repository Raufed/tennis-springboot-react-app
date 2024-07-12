package by.payyzau.tennis.service;

import by.payyzau.tennis.entity.Dish;
import by.payyzau.tennis.exception.NotFoundException;
import by.payyzau.tennis.repository.DishRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepositoty dishRepositoty;
    @Autowired
    private DowloadService dowloadService;
    public Dish getDishById(Long id) {
        Dish dish = dishRepositoty.findById(id).orElseThrow(() -> new NotFoundException("Dish not found!"));
        return dish;
    }
    public List<Dish> allDish () {
        return dishRepositoty.findAll();
    }
    public Dish saveDish(Dish dish) {
        return dishRepositoty.save(dish);
    }
    public void deleteDish(Long id) {
        dishRepositoty.deleteById(id);
    }

    public Dish updateDish(Long id, Dish dish) {
        Dish currentDish = dishRepositoty.findById(id).orElseThrow(() -> new NotFoundException("Dish not found!"));
        currentDish.setName(dish.getName());
        currentDish.setPrice(dish.getPrice());
        currentDish = dishRepositoty.save(currentDish);

        return  currentDish;
    }

    public void showImage(Dish dish) throws GeneralSecurityException, IOException {
        dowloadService.downloadImageFromDrive(dish.getImageUrl());
    }
}
