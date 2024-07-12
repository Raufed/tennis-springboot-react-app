package by.payyzau.tennis.controller;

import by.payyzau.tennis.entity.Dish;
import by.payyzau.tennis.entity.Res;
import by.payyzau.tennis.service.DishService;
import by.payyzau.tennis.service.DowloadService;
import by.payyzau.tennis.service.UploadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/menu")
public class MenuController {
    @Autowired
    private DishService dishService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private DowloadService dowloadService;
    private String urlSaver;
    @GetMapping
    public List<Dish> getAll() throws GeneralSecurityException, IOException {
        List<Dish> dishes = dishService.allDish();
        for (Dish dish: dishes) {
            System.out.println(dish);
            if(dish.getImageUrl() != null) {
                String cleanedUrl = dish.getImageUrl().replace("https://drive.google.com/file/d/", "");
                cleanedUrl = cleanedUrl.replace("/view?usp=drive_link", "");
                dowloadService.downloadImageFromDrive(cleanedUrl);
            }
            else {
                continue;
            }
        }
        return dishService.allDish();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishByID(@PathVariable("id") Long id) throws URISyntaxException{
        Dish dishById = dishService.getDishById(id);
        URI location = new URI("api/v1/menu/" + dishById.getId());
        return ResponseEntity.ok().location(location).body(dishById);
    }
    @PostMapping
    public ResponseEntity<Dish> addDish( @RequestParam("dish") String dish, @RequestParam("image") MultipartFile file) throws URISyntaxException, JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode jsonNode = mapper.readTree(dish);
            Dish dish1 = mapper.treeToValue(jsonNode, Dish.class);
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);
            Res res = uploadService.uploadImageToDrive(tempFile);
            dish1.setImageUrl(res.getUrl());
            System.out.println(res.getUrl());
            Dish savedDish = dishService.saveDish(dish1);
            System.out.println(savedDish.toString());
            return ResponseEntity.created(new URI("api/v1/menu/" + savedDish.getId())).body(savedDish);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }
    @PostMapping("/uploadDishImage")
    public ResponseEntity<String> addImageDish(@RequestParam("image") MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);
            Res res = uploadService.uploadImageToDrive(tempFile);
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
