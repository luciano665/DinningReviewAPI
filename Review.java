package com.example.DinningReviewAPIF.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Review{
    @Id
    @GeneratedValue
    private Long Id;

    private String reviewSub;
    private String submittedBy;
    private Integer overallScore;

    private Long restaurantId;

    private Integer peanutScore;
    private Integer eggScore;
    private Integer diaryScore;
    private Integer estimateSpending;

    private ReviewStatus status;

}