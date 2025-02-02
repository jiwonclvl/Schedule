package com.example.schedule.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private String author;

    private String password;

    private String todo;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Schedule(String author, String password, String todo) {
        this.author = author;
        this.password = password;
        this.todo = todo;
        this.createAt = LocalDateTime.now();//객체 생성 시 현재 시간
        this.updateAt = LocalDateTime.now();
    }



}
