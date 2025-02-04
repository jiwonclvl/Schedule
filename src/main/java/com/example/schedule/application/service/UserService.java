package com.example.schedule.application.service;

import com.example.schedule.application.dto.UserRequestDto;
import com.example.schedule.application.dto.UserResponseDto;

public interface UserService {

    //작성자 생성
    public UserResponseDto createUser(UserRequestDto dto);

    //작성자 수정
    public UserResponseDto updateUser(Long userId, UserRequestDto dto);
}
