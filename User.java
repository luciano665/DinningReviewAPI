package com.example.DinningReviewAPIF.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "app_user")
@Getter
@Setter
@RequiredArgsConstructor
public class User{
    @GeneratedValue
    @Id
    private Long id;
    private String userName;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private Boolean seePeanut;
    private Boolean seeEgg;
    private Boolean seeDiary;
}