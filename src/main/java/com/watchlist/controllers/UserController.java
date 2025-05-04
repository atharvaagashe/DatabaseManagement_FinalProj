package com.watchlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.watchlist.models.User;
import com.watchlist.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt())); // Securely hash password here
        userService.registerUser(user);
        return "redirect:/login";
    }
    

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                    @RequestParam String password,
                    HttpSession session,
                    RedirectAttributes redirectAttributes) {
    if (userService.authenticateUser(username, password)) {
        session.setAttribute("username", username);

        // Set userId in session
        Long userId = userService.getUserIdByUsername(username); // You must implement this
        session.setAttribute("userId", userId);

        return "redirect:/dashboard";
    } else {
        redirectAttributes.addFlashAttribute("error", "Invalid username or password");
        return "redirect:/login";
    }
}

@GetMapping("/user")
public String showUserInfo(HttpSession session, Model model) {
    Long userIdLong = (Long) session.getAttribute("userId");

    if (userIdLong != null) {
        int userId = userIdLong.intValue();  // Convert Long to int
        User user = userService.getUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "user"; // corresponds to user.mustache
        } else {
            return "redirect:/dashboard"; // fallback if user not found
        }
    } else {
        return "redirect:/login";
    }
}


}
