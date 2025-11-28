package com.tasteandtwist.controller;

import com.tasteandtwist.model.Order;
import com.tasteandtwist.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepo.findAll());
        return "orders"; // orders.html
    }

    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderRepo.findById(id).orElse(null);

        if (order == null) {
            model.addAttribute("error", "Order not found");
            return "orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("items", order.getItems());

        return "order-details"; // FIXED
    }
}
