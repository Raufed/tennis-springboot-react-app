package by.payyzau.tennis.controller;

import by.payyzau.tennis.entity.Dish;
import by.payyzau.tennis.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/menu")
public class MenuController {
    @Autowired
    private DishService dishService;
    @GetMapping
    public List<Dish> getAll() {
        return dishService.allDish();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishByID(@PathVariable("id") Long id) throws URISyntaxException{
        Dish dishById = dishService.getDishById(id);
        URI location = new URI("api/v1/menu/" + dishById.getId());
        return ResponseEntity.ok().location(location).body(dishById);
    }
    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish) throws URISyntaxException {
        Dish savedDish = dishService.saveDish(dish);
        return ResponseEntity.created(new URI("api/v1/menu/" + savedDish.getId())).body(savedDish);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable("id") Long id, @RequestBody Dish dish) throws URISyntaxException {
        Dish updatedDish = dishService.updateDish(id, dish);
        URI location = new URI("api/v1/menu/" + id);
        return ResponseEntity.ok().location(location).body(updatedDish);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable("id") Long id) throws URISyntaxException {
         dishService.deleteDish(id);
         URI location = new URI("api/v1/menu/" + id);
         return ResponseEntity.ok().location(location).body("Dish with id = " + id + " was deleted!");
    }
}
