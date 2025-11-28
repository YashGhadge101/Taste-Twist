package com.tasteandtwist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model) {

        // If you are not using login system, we show static admin details
        model.addAttribute("user", new UserProfile(
                "Admin User",
                "admin@tasteandtwist.com"
        ));

        return "profile"; // profile.html inside templates
    }
}

// SIMPLE PROFILE DATA MODEL (NO SECURITY SYSTEM)
class UserProfile {
    private String name;
    private String email;

    public UserProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}
