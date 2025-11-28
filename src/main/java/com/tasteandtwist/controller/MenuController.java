package com.tasteandtwist.controller;

import com.tasteandtwist.model.Menu;
import com.tasteandtwist.model.Restaurant;
import com.tasteandtwist.repository.MenuRepository;
import com.tasteandtwist.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    // List all menus
    @GetMapping
    public String listMenus(Model model) {
        model.addAttribute("menus", menuRepo.findAll());
        return "menus";
    }

    // Add menu page
    @GetMapping("/add")
    public String addMenuPage(Model model) {
        model.addAttribute("restaurants", restaurantRepo.findAll());
        return "add-menu";
    }

    // Handle add submit
    @PostMapping("/add")
    public String saveMenu(@RequestParam String name,
                           @RequestParam(required = false) String price,
                           @RequestParam Long restaurantId) {

        Restaurant r = restaurantRepo.findById(restaurantId).orElse(null);

        Menu m = new Menu();
        m.setName(name);

        // Convert String → BigDecimal safely
        if (price != null && !price.isBlank()) {
            m.setPrice(new BigDecimal(price));
        } else {
            m.setPrice(BigDecimal.ZERO); // default
        }

        m.setRestaurant(r);

        menuRepo.save(m);

        return "redirect:/menus";
    }

    // Edit page
    @GetMapping("/edit/{id}")
    public String editMenuPage(@PathVariable Long id, Model model) {
        Menu m = menuRepo.findById(id).orElse(null);
        model.addAttribute("menu", m);
        model.addAttribute("restaurants", restaurantRepo.findAll());
        return "edit-menu";
    }

    // Handle edit submit
    @PostMapping("/edit/{id}")
    public String updateMenu(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam(required = false) String price,
                             @RequestParam Long restaurantId) {

        Menu m = menuRepo.findById(id).orElse(null);
        Restaurant r = restaurantRepo.findById(restaurantId).orElse(null);

        if (m != null) {
            m.setName(name);

            // Convert String → BigDecimal properly
            if (price != null && !price.isBlank()) {
                m.setPrice(new BigDecimal(price));
            }

            m.setRestaurant(r);

            menuRepo.save(m);
        }

        return "redirect:/menus";
    }
}
