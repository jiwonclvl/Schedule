package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    //아이디
    private final Long id;

    //할일
    private final String todo;

    //작성자명
    private final String author;

    // 작성일
    private final String createAt;

    // 수정일
    private final String updateAt;

    public ScheduleResponseDto(Long id, String author, String todo, String creatAt, String updateAt) {
        this.id = id;
        this.author = author;
        this.todo = todo;
        this.createAt = creatAt;
        this.updateAt = updateAt;
    }


}
