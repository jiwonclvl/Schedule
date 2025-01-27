package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    //작성자명
    private String author;

    //비밀번호
    private String password;

    //할일
    private String todo;

    //작성일
    private LocalDateTime createAt;

    //수정일
    private LocalDateTime updateAt;

    public Schedule(String author, String password, String todo) {
        this.author = author;
        this.password = password;
        this.todo = todo;
        this.createAt = LocalDateTime.now(); //객체 생성 시 현재 시간
        this.updateAt = LocalDateTime.now();
    }



}
