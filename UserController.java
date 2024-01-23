package com.example.DinningReviewAPIF.controller;

import com.example.DinningReviewAPIF.model.User;
import com.example.DinningReviewAPIF.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/users")
@RestController
public class UserController{
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewUser(@RequestBody User user){
        validateNewUser(user);

        userRepository.save(user);
    }
    @GetMapping("/userName")
    public User getUser(@PathVariable String userName){
        validateUserName(userName);

        Optional<User> optionalPreExistingUser = userRepository.findUserByUserName(userName);
        if(!optionalPreExistingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User existingUser = optionalPreExistingUser.get();
        existingUser.setId(null);

        return existingUser;
    }

    @PutMapping("/userName")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserInfo(@PathVariable String userName, @RequestBody User updateUser){
        validateUserName(userName);

        Optional<User> optionalPreExistingUser = userRepository.findUserByUserName(userName);
        if(optionalPreExistingUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User existingUser = optionalPreExistingUser.get();
        copyUserInfo(updateUser, existingUser);
        userRepository.save(existingUser);
    }

    private void copyUserInfo(User updateUser, User existingUser){
        if(ObjectUtils.isEmpty(updateUser.getUserName())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(!ObjectUtils.isEmpty(updateUser.getCountry())){
            existingUser.setCountry(updateUser.getCountry());
        }
        if(!ObjectUtils.isEmpty(updateUser.getCity())){
            existingUser.setCity(updateUser.getCity());
        }
        if(!ObjectUtils.isEmpty(updateUser.getState())){
            existingUser.setState(updateUser.getState());
        }
        if(!ObjectUtils.isEmpty(updateUser.getZipCode())){
            existingUser.setZipCode(updateUser.getZipCode());
        }
        if(!ObjectUtils.isEmpty(updateUser.getSeePeanut())){
            existingUser.setSeePeanut(updateUser.getSeePeanut());
        }
        if(!ObjectUtils.isEmpty(updateUser.getSeeDiary())){
            existingUser.setSeeDiary(updateUser.getSeeDiary());
        }
        if(!ObjectUtils.isEmpty(updateUser.getSeeEgg())){
            existingUser.setSeeEgg(updateUser.getSeeEgg());
        }
    }

    private void validateNewUser(User user){
        validateUserName(user.getUserName());

        Optional<User> existingUser = userRepository.findUserByUserName(user.getUserName());
        if(existingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    private void validateUserName(String userName){
        if(ObjectUtils.isEmpty(userName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
