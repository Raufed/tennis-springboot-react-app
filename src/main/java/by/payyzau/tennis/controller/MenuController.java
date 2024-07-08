package by.payyzau.tennis.controller;

import by.payyzau.tennis.entity.Dish;
import by.payyzau.tennis.entity.Res;
import by.payyzau.tennis.service.DishService;
import by.payyzau.tennis.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/menu")
public class MenuController {
    @Autowired
    private DishService dishService;
    @Autowired
    private UploadService uploadService;
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
    @PostMapping("/uploadDishImage")
    public ResponseEntity<String> addImageDish(@RequestParam("image") MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);
            Res res = uploadService.uploadImageToDrive(tempFile);
            System.out.println(res);
            return ResponseEntity.ok().body(res.toString());
        } catch (IOException e) {
            // Обработка исключения IOException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
        }

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
