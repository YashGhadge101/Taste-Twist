package com.tasteandtwist.controller;

import com.tasteandtwist.model.Restaurant;
import com.tasteandtwist.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepo;

    // List all restaurants
    @GetMapping
    public String listRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantRepo.findAll());
        return "restaurants";
    }

    // Show Add Page
    @GetMapping("/add")
    public String addRestaurantPage(Model model) {
        return "add-restaurant";
    }

    // Handle Add Submit
    @PostMapping("/add")
    public String saveRestaurant(@RequestParam String name,
                                 @RequestParam String address) {

        Restaurant r = new Restaurant();
        r.setName(name);
        r.setAddress(address);
        restaurantRepo.save(r);

        return "redirect:/restaurants";
    }

    // Show Edit Page
    @GetMapping("/edit/{id}")
    public String editRestaurantPage(@PathVariable Long id, Model model) {
        Restaurant r = restaurantRepo.findById(id).orElse(null);
        model.addAttribute("restaurant", r);
        return "edit-restaurant";
    }

    // Handle Edit Submit
    @PostMapping("/edit/{id}")
    public String updateRestaurant(@PathVariable Long id,
                                   @RequestParam String name,
                                   @RequestParam String address) {

        Restaurant r = restaurantRepo.findById(id).orElse(null);
        if (r != null) {
            r.setName(name);
            r.setAddress(address);
            restaurantRepo.save(r);
        }

        return "redirect:/restaurants";
    }

    // View Details
    @GetMapping("/view/{id}")
    public String viewRestaurant(@PathVariable Long id, Model model) {
        Restaurant r = restaurantRepo.findById(id).orElse(null);
        model.addAttribute("restaurant", r);
        return "restaurant-details";
    }
}
