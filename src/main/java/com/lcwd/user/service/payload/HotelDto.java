package com.lcwd.user.service.payload;

import lombok.Data;

@Data
public class HotelDto {
    private String id;
    private String name;
    private String location;
    private String about;
}