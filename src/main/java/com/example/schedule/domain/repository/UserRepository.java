package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;

public interface UserRepository {
    public UserResponseDto saveUser(User user);
}
