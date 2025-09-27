package com.lcwd.user.service.external.service;

import com.lcwd.user.service.payload.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
    //get
    @GetMapping("/ratings/users/{userId}")
    ResponseEntity<List<RatingDto>> getRatingByUserId(@PathVariable String userId);

    //creating Rating
    @PostMapping("/ratings")
    RatingDto createRating(@RequestBody RatingDto ratingDto);

    //Put

}
