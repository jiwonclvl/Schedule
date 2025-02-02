package com.example.schedule.application.dto;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private  Long scheduleId;

    private  Long userId;

    private  String todo;

    private  String createAt;

    private  String updateAt;

    public ScheduleResponseDto(Long scheduleId, Long userId, String todo, String createAt, String updateAt) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.todo = todo;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
