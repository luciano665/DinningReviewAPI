package com.example.DinningReviewAPIF.controller;

import com.example.DinningReviewAPIF.model.Restaurant;
import com.example.DinningReviewAPIF.model.Review;
import com.example.DinningReviewAPIF.model.ReviewStatus;
import com.example.DinningReviewAPIF.model.User;
import com.example.DinningReviewAPIF.repository.RestaurantRepository;
import com.example.DinningReviewAPIF.repository.ReviewRepository;
import com.example.DinningReviewAPIF.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/reviews")
@RestController
public class ReviewController{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(ReviewRepository reviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserReview(@RequestBody Review review){
        validateUserReview(review);

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(review.getRestaurantId());
        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        review.setStatus(ReviewStatus.PENDING);
        reviewRepository.save(review);
    }
    private void validateUserReview(Review review){
        if(ObjectUtils.isEmpty(review.getSubmittedBy())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(ObjectUtils.isEmpty(review.getRestaurantId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(ObjectUtils.isEmpty(review.getDiaryScore()) && ObjectUtils.isEmpty(review.getEggScore()) && ObjectUtils.isEmpty(review.getPeanutScore())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findUserByUserName(review.getSubmittedBy());
        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
