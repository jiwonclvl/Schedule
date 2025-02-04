package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;

public interface UserRepository {

    //작성자 등록
    public UserResponseDto saveUser(User user);

    //작성자 정보 조회
    public UserResponseDto findUser(Long userId);

    // 작성자 수정
    public void updateUser(Long userId, String author, String password);

}
