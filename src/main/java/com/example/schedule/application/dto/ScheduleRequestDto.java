package com.example.schedule.application.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    //유저 아이디
    private Long userId;

    private String password;

    private String todo;

}
