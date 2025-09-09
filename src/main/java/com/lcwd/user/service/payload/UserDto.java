package com.lcwd.user.service.payload;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String userId;
    private String name;
    private String email;
    private String about;
}
