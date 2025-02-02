package com.example.schedule.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {

    private String author;

    private String password;

    private String email;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public User(String author, String password, String email) {
        this.author = author;
        this.password = password;
        this.email = email;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }


}
