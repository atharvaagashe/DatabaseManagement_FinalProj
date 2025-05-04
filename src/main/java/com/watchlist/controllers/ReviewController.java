package com.watchlist.controllers;

import com.watchlist.models.Review;
import com.watchlist.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/reviews/{movieId}")
    public String getReviews(@PathVariable Long movieId, Model model) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("review", new Review());
        model.addAttribute("movieId", movieId);
        return "reviews";
    }

    @GetMapping("/user-reviews")
    public String getUserReviews(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L; // fallback
        }
        List<Review> userReviews = reviewRepository.findByUserId(userId);
        model.addAttribute("userReviews", userReviews);
        return "user_reviews";
    }

    @PostMapping("/reviews")
    public String addReview(@ModelAttribute Review review, HttpSession session) {
        review.setTimestamp(LocalDateTime.now());

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L; // fallback
        }
        review.setUserId(userId);

        reviewRepository.save(review);
        return "redirect:/reviews/" + review.getMovieId();
    }
    
    @PostMapping("/update-review-rating")
    public String updateReviewRating(@RequestParam Long reviewId, @RequestParam int rating) {
        reviewRepository.updateRating(reviewId, rating);
        return "redirect:/user-reviews";
    }
}
