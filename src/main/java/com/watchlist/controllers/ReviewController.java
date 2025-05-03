// package com.watchlist.controllers;

// import com.watchlist.models.Review;
// import com.watchlist.repository.ReviewRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
// import java.util.List;

// @Controller
// public class ReviewController {

//     @Autowired
//     private ReviewRepository reviewRepository;

//     @GetMapping("/reviews/{movieId}")
//     public String getReviews(@PathVariable Long movieId, Model model) {
//         List<Review> reviews = reviewRepository.findByMovieId(movieId);
//         model.addAttribute("reviews", reviews);
//         model.addAttribute("review", new Review());
//         model.addAttribute("movieId", movieId);
//         return "reviews"; // reviews.mustache
//     }

//     @PostMapping("/reviews")
//     public String addReview(@ModelAttribute Review review) {
//         review.setTimestamp(LocalDateTime.now());

//         // Fallback/default user ID if not logged in (replace with session-based later if needed)
//         if (review.getUserId() == null) {
//             review.setUserId(1L);
//         }

//         reviewRepository.save(review);
//         return "redirect:/reviews/" + review.getMovieId();
//     }
    
// }

package com.watchlist.controllers;

import com.watchlist.models.Review;
import com.watchlist.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "reviews"; // reviews.mustache
    }

    @PostMapping("/reviews")
    public String addReview(@ModelAttribute Review review) {
        review.setTimestamp(LocalDateTime.now());

        // Use default user ID for now
        if (review.getUserId() == null) {
            review.setUserId(1L);
        }

        reviewRepository.save(review);
        return "redirect:/reviews/" + review.getMovieId(); // Redirect back to reviews page
    }
}
