package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    //할일
    private String todo;

    //작성자명
    private String author;

    //비밀번호
    private String password;

}
