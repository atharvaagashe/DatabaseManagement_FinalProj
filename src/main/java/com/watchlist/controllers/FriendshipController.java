package com.watchlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watchlist.models.User;
import com.watchlist.services.UserService;
import com.watchlist.services.FriendshipService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FriendshipController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/users-list")
    public String showFriendsPage(HttpSession session, Model model) {
        // Check if user is logged in
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        // Get the current user ID
        Long currentUserIdLong = (Long) session.getAttribute("userId");
        int currentUserId = currentUserIdLong != null ? currentUserIdLong.intValue() : 0;
        
        // Get all users
        List<User> allUsers = userService.getAllUsers();
        
        // Transform the list to include the isCurrentUser and isFriend flags
        List<Map<String, Object>> userMaps = allUsers.stream().map(user -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", user.getUserId());
            userMap.put("username", user.getUsername());
            userMap.put("email", user.getEmail());
            userMap.put("isCurrentUser", user.getUserId() == currentUserId);
            userMap.put("isFriend", friendshipService.areFriends(currentUserId, user.getUserId()));
            return userMap;
        }).collect(Collectors.toList());
        
        // Add data to the model
        model.addAttribute("allUsers", userMaps);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("username", username);
        
        return "friends";
    }
    
    @GetMapping("/add-friend/{friendId}")
    public String addFriend(@PathVariable("friendId") int friendId, 
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        // Check if user is logged in
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        // Get current user ID
        Long currentUserIdLong = (Long) session.getAttribute("userId");
        if (currentUserIdLong == null) {
            return "redirect:/login";
        }
        
        int currentUserId = currentUserIdLong.intValue();
        
        // Add friendship
        boolean success = friendshipService.addFriend(currentUserId, friendId);
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Friend added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add friend.");
        }
        
        return "redirect:/users-list";
    }
    
    @GetMapping("/remove-friend/{friendId}")
    public String removeFriend(@PathVariable("friendId") int friendId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        // Check if user is logged in
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        
        // Get current user ID
        Long currentUserIdLong = (Long) session.getAttribute("userId");
        if (currentUserIdLong == null) {
            return "redirect:/login";
        }
        
        int currentUserId = currentUserIdLong.intValue();
        
        // Remove friendship
        boolean success = friendshipService.removeFriend(currentUserId, friendId);
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Friend removed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to remove friend.");
        }
        
        return "redirect:/users-list";
    }
}
