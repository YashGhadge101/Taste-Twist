package com.tasteandtwist.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, precision = 10, scale = 2)
    private BigDecimal price;  // ✅ BigDecimal for money

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // ---------- CONSTRUCTORS ----------

    public Menu() {}

    public Menu(String name, BigDecimal price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    // ---------- GETTERS & SETTERS ----------

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }  // ❗ Only BigDecimal allowed

    public Restaurant getRestaurant() { return restaurant; }

    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
}
