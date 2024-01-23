package com.example.DinningReviewAPIF.controller;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.DinningReviewAPIF.model.Restaurant;
import com.example.DinningReviewAPIF.repository.RestaurantRepository;


@RequestMapping("/restaurants")
@RestController
public class RestaurantController{
    private final RestaurantRepository restaurantRepository;
    private final Pattern zipCode = Pattern.compile("\\d{5}");
    private static final Pattern zipCodePattern = Pattern.compile("your_regex_here");


    public RestaurantController(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant){

        validatationOfRestaurant(restaurant);

        return restaurantRepository.save(restaurant);
    }
    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id){
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if(restaurant.isPresent()){
            return restaurant.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public Iterable <Restaurant> searchRestaurants(@RequestParam String zipCode, @RequestParam String allergy){
        validationOfZipcode(zipCode);

        Iterable <Restaurant> restaurants = Collections.EMPTY_LIST;
        if(allergy.equalsIgnoreCase("peanut")){
            restaurants = restaurantRepository.findByZipCodeOrderByPeanutAsc(zipCode);
        }else if(allergy.equalsIgnoreCase("Egg")){
            restaurants = restaurantRepository.findByZipCodeOrderByEggAsc(zipCode);
        }else if(allergy.equalsIgnoreCase("Diary")){
            restaurants = restaurantRepository.findByZipCodeOrderByDiaryAsc(zipCode);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return restaurants;
    }
    private void validatationOfRestaurant(Restaurant restaurant){
        if(ObjectUtils.isEmpty(restaurant.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Restaurant> preExistingRestaurant  = restaurantRepository.findByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
        if(preExistingRestaurant.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    private void validationOfZipcode(String zipCode){
        Matcher matcher = zipCodePattern.matcher(zipCode);
        boolean isZipCodeValid = matcher.matches();
        if(!isZipCodeValid){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    


}