package com.writingService.writingService.User;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

}
