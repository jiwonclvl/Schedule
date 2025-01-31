package com.example.schedule.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
