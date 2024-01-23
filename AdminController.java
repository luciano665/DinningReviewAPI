package com.example.DinningReviewAPIF.controller;
import com.example.DinningReviewAPIF.model.AdminReview;
import com.example.DinningReviewAPIF.model.Restaurant;
import com.example.DinningReviewAPIF.model.Review;
import com.example.DinningReviewAPIF.model.ReviewStatus;
import com.example.DinningReviewAPIF.repository.RestaurantRepository;
import com.example.DinningReviewAPIF.repository.ReviewRepository;
import com.example.DinningReviewAPIF.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@RequestMapping("/admin")
@RestController
public class AdminController{
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminController(UserRepository userRepository, RestaurantRepository restaurantRepository, ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/reviews")
    public List<Review> getReviewStatus(@RequestParam String review_Status){
        ReviewStatus reviewStatus = ReviewStatus.PENDING;
        try{
            reviewStatus = ReviewStatus.valueOf(review_Status.toUpperCase());
        }catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return reviewRepository.findReviewByStatus(reviewStatus);
    }
    @PutMapping("/reviews/{reviewId}")
    public void performReview(@PathVariable Long reviewId, @RequestBody AdminReview adminReviewAction){
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Review review = optionalReview.get();

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(review.getRestaurantId());
        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(adminReviewAction.getAccept()){
            review.setStatus(ReviewStatus.ACCEPTED);
        }else{
            review.setStatus(ReviewStatus.REJECTED);
        }

        reviewRepository.save(review);
        updateRestaurantScores(optionalRestaurant.get());

    }

    private void updateRestaurantScores(Restaurant restaurant){
        List<Review> reviews = reviewRepository.findReviewByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.ACCEPTED);
        if(reviews.size() == 0){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        int sumPeanut = 0;
        int totalPeanut = 0;
        int diarySum = 0;
        int totalDiary = 0;
        int eggsSum = 0;
        int totalEgg = 0;

        for(Review s : reviews){
            if(!ObjectUtils.isEmpty(s.getDiaryScore())){
                diarySum += s.getDiaryScore();
                totalDiary++;
            }
            if(!ObjectUtils.isEmpty(s.getPeanutScore())){
                sumPeanut += s.getPeanutScore();
                totalPeanut++;
            }
            if(!ObjectUtils.isEmpty(s.getEggScore())){
                eggsSum += s.getEggScore();
                totalEgg++;
            }
        }
        int totalSum = eggsSum + sumPeanut + diarySum;
        int finalTotal = totalPeanut + totalEgg + totalDiary;

        float overallScore = (float) totalSum / finalTotal;
        restaurant.setOverallScore(String.format("%.2f",overallScore));

        if(totalPeanut > 0){
            float peanutScore = (float) sumPeanut / totalPeanut;
            restaurant.setPeanut(String.format("%.2f", peanutScore));
        }
        if(totalDiary > 0){
            float diaryScore = (float) diarySum / totalDiary;
            restaurant.setDiary(String.format("%.2f", diaryScore));
        }
        if(totalEgg > 0){
            float eggScore = (float) eggsSum / totalEgg;
            restaurant.setEgg(String.format("%.2f", eggScore));
        }
        restaurantRepository.save(restaurant);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestParam(required = false) String cousineType, @RequestParam(required = false) String priceRange){
        List<Restaurant> restaurants;
        if(!ObjectUtils.isEmpty(cousineType) && !ObjectUtils.isEmpty(priceRange)){
            restaurants = restaurantRepository.findByCousineTypeAndPriceRange(cousineType, priceRange);
        }
        else if(!ObjectUtils.isEmpty(cousineType)){
            restaurants = restaurantRepository.findByCousineType(cousineType);
        }
        else if(!ObjectUtils.isEmpty(priceRange)){
            restaurants = restaurantRepository.findByPriceRange(priceRange);
        }
        else{
            restaurants = (List<Restaurant>) restaurantRepository.findAll();
        }
        return ResponseEntity.ok(restaurants);
    }

}