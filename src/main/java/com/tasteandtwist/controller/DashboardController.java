package com.tasteandtwist.controller;

import com.tasteandtwist.repository.MenuRepository;
import com.tasteandtwist.repository.OrderRepository;
import com.tasteandtwist.repository.RestaurantRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired private RestaurantRepository restaurantRepo;
    @Autowired private MenuRepository menuRepo;
    @Autowired private OrderRepository orderRepo;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        model.addAttribute("userName", session.getAttribute("userName"));

        // SAFE values for Thymeleaf
        model.addAttribute("restaurants", restaurantRepo.findAll());
        model.addAttribute("menus", menuRepo.findAll());
        model.addAttribute("orders", orderRepo.findAll());

        model.addAttribute("restaurantCount", restaurantRepo.count());
        model.addAttribute("menuCount", menuRepo.count());
        model.addAttribute("orderCount", orderRepo.countPendingOrders()); // if exists

        return "dashboard";
    }
}
