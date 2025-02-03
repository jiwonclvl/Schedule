package com.example.schedule.application.dto;

import lombok.Getter;

@Getter
public class ScheduleCreateRequestDto {

    private Long userId;

    private String password;

    private String todo;

}
