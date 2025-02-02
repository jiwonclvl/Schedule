package com.example.schedule.docs;

import com.example.schedule.application.dto.UserRequestDto;
import com.example.schedule.application.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 관리", description = "사용자 관리 API입니다.")
public interface UserControllerDocs {

    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto);
}
