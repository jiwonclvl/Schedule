package com.example.schedule.application.service;

import com.example.schedule.application.dto.UserRequestDto;
import com.example.schedule.application.dto.UserResponseDto;

public interface UserService {
    public UserResponseDto createUser(UserRequestDto dto);
}
