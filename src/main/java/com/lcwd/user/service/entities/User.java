package com.lcwd.user.service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "micro_users")
@Data
public class User {

    @Id
    private String userId;

    @Column(length = 20)
    private String name;
    private String email;
    private String about;

    @Transient
    private List<Rating> ratingList;
}
