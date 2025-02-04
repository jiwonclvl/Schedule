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

        //객체 생성 시 현재 시간
        this.createAt = LocalDateTime.now();

        //첫 작성자 등록 시 수정일 = 작성일
        this.updateAt = LocalDateTime.now();
    }


}
