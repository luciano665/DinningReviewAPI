package com.example.DinningReviewAPIF.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.DinningReviewAPIF.model.Restaurant;


public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{
    Optional<Restaurant> findByNameAndZipCode(String name ,String zipCode);
    List<Restaurant> findByZipCodeOrderByPeanutAsc(String zipCode);
    List<Restaurant> findByZipCodeOrderByEggAsc(String zipCode);
    List<Restaurant> findByZipCodeOrderByDiaryAsc(String zipCode);
    List<Restaurant> findByCousineType(String cousineType);
    List<Restaurant> findByPriceRange(String priceRange);
    List<Restaurant> findByCousineTypeAndPriceRange(String cousineType, String priceRange);
    

}