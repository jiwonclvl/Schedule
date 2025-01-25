package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    //아이디
    private final Long id;

    //할일
    private final String todo;

    //작성자명
    private final String author;

    //비밀번호
    private final String password;

    // 작성일
    private final String createAt;

    // 수정일
    private final String updateAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.author = schedule.getAuthor();
        this.password = schedule.getPassword();
        this.createAt = schedule.getCreateAt();
        this.updateAt = schedule.getCreateAt();
    }

}
