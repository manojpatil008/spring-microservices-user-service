package com.lcwd.user.service.external.service;

import com.lcwd.user.service.payload.HotelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {
    @GetMapping("/hotels/{hotelId}")
    HotelDto getHotel(@PathVariable String hotelId);
}
