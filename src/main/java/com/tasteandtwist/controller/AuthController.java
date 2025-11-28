package com.tasteandtwist.controller;

import com.tasteandtwist.model.User;
import com.tasteandtwist.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "Login";
    }

    @PostMapping("/login-process")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {

        User user = userService.findByEmail(username);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid email or password");
            return "Login";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());

        return "redirect:/dashboard";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup-process")
    public String processSignup(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists");
            return "signup";
        }

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(password);
        u.setVerified(false);
        u.setAdmin(false);

        userService.save(u);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
