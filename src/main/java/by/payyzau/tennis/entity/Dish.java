package by.payyzau.tennis.entity;

import jakarta.persistence.*;

@Entity
public class Dish {

    public Dish(String name, double price, Long id, String imageId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageId = imageId;
    }
    public Dish() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "imageId")
    private String imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
