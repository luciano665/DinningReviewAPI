package com.example.DinningReviewAPIF.repository;

import com.example.DinningReviewAPIF.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{

    Optional<User> findUserByUserName(String userName);
}