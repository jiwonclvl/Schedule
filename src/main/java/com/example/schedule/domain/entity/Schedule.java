package com.example.schedule.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long userId;

    private String todo;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Schedule(Long userId, String todo) {
        this.userId = userId;
        this.todo = todo;

        //객체 생성 시 현재 시간
        this.createAt = LocalDateTime.now();

        //첫 일정 등록 시 수정일 = 작성일
        this.updateAt = LocalDateTime.now();
    }
}
