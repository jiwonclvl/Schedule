package com.example.schedule.docs;

import com.example.schedule.application.dto.UserRequestDto;
import com.example.schedule.application.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 관리", description = "사용자 관리 API입니다.")
public interface UserControllerDocs {

    @Operation(summary = "사용자 등록", description = "작성자명, 비밀번호, 이메일을 입력받아 사용자를 등록합니다. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "400", description = "작성자명, 비밀번호 미입력으로 일정 등록에 실패하였습니다.")
    })
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody UserRequestDto dto);

    @Operation(summary = "사용자 수정", description = "사용자의 `작성자명(author)`만 수정 가능하며, 올바른 비밀번호 입력 시 사용자 수정이 가능합니다.")
    public ResponseEntity<UserResponseDto> updateUser(
            @Parameter(description = "수정할 사용자 ID")
            @PathVariable Long userId,

            @RequestBody UserRequestDto dto
    );
}
