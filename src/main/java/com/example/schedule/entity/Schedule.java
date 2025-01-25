package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    //사용자 아이디
    private Long id;
    //할일
    private String todo;

    //작성자명
    private String author;

    //비밀번호
    private String password;

    //작성일
    private String createAt;

    //수정일
    private String updateAt;

}
