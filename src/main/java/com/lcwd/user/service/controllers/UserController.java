package com.lcwd.user.service.controllers;

import com.lcwd.user.service.external.service.RatingService;
import com.lcwd.user.service.payload.ApiResponse;
import com.lcwd.user.service.payload.RatingDto;
import com.lcwd.user.service.payload.UserDto;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto userDto1 = userService.saveUser(userDto);

        return new ResponseEntity<UserDto>(userDto1, HttpStatus.CREATED);
    }

    //get all users
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUsers = userService.getAllUser();
        return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.OK);
    }

    int retryCount = 1;

    //get single user
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        logger.info("Retry count : {}", retryCount);
        retryCount++;
        UserDto userDto = userService.getUser(userId);
        return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
    }

    //Fall back method for circuitbreaker
    public ResponseEntity<UserDto> ratingHotelFallback(String userId,Exception ex){
        logger.info("Fallback is executed because service is down : " + ex.getMessage());
        UserDto userDto = UserDto.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId("13322")
                .build();
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    //detete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",HttpStatus.OK),HttpStatus.OK);
    }

    //update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId){
        UserDto userDto1 = userService.updateUser(userDto, userId);
        return new ResponseEntity<UserDto>(userDto1,HttpStatus.OK);
    }

    //Creating Rating for user
    @PostMapping("/{userId}/ratings")
    public ResponseEntity<RatingDto> creatingRatingForUser(@PathVariable String userId, @RequestBody RatingDto ratingDto){
        ratingDto.setUserId(userId);
        RatingDto rating = ratingService.createRating(ratingDto);
        return new ResponseEntity<RatingDto>(rating,HttpStatus.OK);
    }
}
