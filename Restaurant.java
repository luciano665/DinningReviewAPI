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
public class Restaurant {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String city;
    private String country;
    private String state;
    private String website;
    private String phoneNumber;
    private String zipCode;
    private String overallScore;
    private String peanut;
    private String egg;
    private String diary;
    private String cousineType;
    private String priceRange;


}
