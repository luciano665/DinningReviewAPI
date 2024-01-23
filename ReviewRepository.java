package com.example.DinningReviewAPIF.repository;
import com.example.DinningReviewAPIF.model.Review;
import com.example.DinningReviewAPIF.model.ReviewStatus;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long>{
    List<Review> findReviewByStatus(ReviewStatus reviewStatus);
    List<Review> findReviewByRestaurantIdAndStatus(Long restaurantId, ReviewStatus reviewStatus);

}
