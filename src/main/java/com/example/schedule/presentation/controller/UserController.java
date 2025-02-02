package com.example.schedule.presentation.controller;

import com.example.schedule.application.dto.UserRequestDto;
import com.example.schedule.application.dto.UserResponseDto;
import com.example.schedule.application.service.UserService;
import com.example.schedule.docs.UserControllerDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto) {
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequestDto dto
    ) {
        return new ResponseEntity<>(userService.updateUser(userId, dto), HttpStatus.OK);
    }


}
