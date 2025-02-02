package com.example.schedule.application.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    Long userId;

    String author;

    String createAt;

    String updateAt;

    public UserResponseDto(Long userId, String author, String createAt, String updateAt) {
        this.userId = userId;
        this.author = author;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
