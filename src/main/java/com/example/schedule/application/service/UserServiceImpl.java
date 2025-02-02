package com.example.schedule.application.service;

import com.example.schedule.application.dto.UserRequestDto;
import com.example.schedule.application.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;
import com.example.schedule.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserResponseDto createUser(UserRequestDto dto) {

        if (isRequiredFieldEmpty(dto.getAuthor(), dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자명과 비밀번호는 반드시 입력되어야 합니다.");
        }

        User user = new User(dto.getAuthor(), dto.getPassword(), dto.getEmail());
        return userRepository.saveUser(user);
    }

    private boolean isRequiredFieldEmpty(String value1, String value2) {
        return !StringUtils.hasText(value1) || !StringUtils.hasText(value2);
    }
}
