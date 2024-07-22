package by.payyzau.tennis.service;

import by.payyzau.tennis.entity.Dish;
import by.payyzau.tennis.repository.DishRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DishServiceTest {
    @InjectMocks
    private  DishService dishService;
    @Mock
    private DishRepository dishRepository;
    private List<Dish> dishes;

    @BeforeEach
    void setUp() {
        System.out.println("---SET UP---");

        Dish dish2 = new Dish(19L,"Отбивная куриная", 7.0,"gQrt-56Jj-ib","https://youtube.com");
        dishes = List.of(dish2);

        //Mockito.when(dishRepository.saveAll(Mockito.anyList())).thenReturn(dishes);

        //List<Dish> savedDishes = dishRepository.saveAll(dishes);
    }

    @AfterEach
    void tearDown() {

        /*
        Mockito.when(dishRepository.findAll()).thenReturn(Mockito.anyList());
        List<Dish> dishListForDeleting = dishRepository.findAll();
        System.out.println("Dish For Deleting = " + dishListForDeleting);
        dishRepository.deleteAll(dishes);
        */
        System.out.println("---TEAR DOWN---");
    }

    @Test
    void saveDish() {
        Dish dish = new Dish(18L,"Котлета", 5.0,"jj7-fHrED-y2","https://google.com");
        Mockito.when(dishRepository.save(Mockito.any(Dish.class))).thenReturn(dish);

        Dish savedDish = dishService.saveDish(dish);

        org.assertj.core.api.Assertions.assertThat(savedDish).isNotNull();
        org.assertj.core.api.Assertions.assertThat(savedDish).isEqualTo(savedDish);
        //List<Dish> dishList = dishService.allDish();
        //System.out.println(dishList);
    }
}