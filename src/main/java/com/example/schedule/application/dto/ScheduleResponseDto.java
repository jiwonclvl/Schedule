package com.example.schedule.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    //아이디
    private final Long id;

    //할일
    private final String todo;

    //작성자명
    private final String author;

    // 작성일
    private final LocalDateTime createAt;

    // 수정일
    private final LocalDateTime updateAt;

    public ScheduleResponseDto(Long id, String author, String todo, LocalDateTime creatAt, LocalDateTime updateAt) {
        this.id = id;
        this.author = author;
        this.todo = todo;
        this.createAt = creatAt;
        this.updateAt = updateAt;
    }


}
