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
    @GetMapping()
    public List<Dish> getAll() {
        return dishService.allDish();
    }
    @PostMapping("createDish")
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish) throws URISyntaxException {
        Dish savedDish = dishService.saveDish(dish);
        return ResponseEntity.created(new URI("api/v1/menu/"+savedDish.getId())).body(savedDish);
    }
}
