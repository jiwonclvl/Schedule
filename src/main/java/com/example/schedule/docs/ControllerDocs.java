package com.example.schedule.docs;


import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "일정 관리", description = "일정 관리 API입니다.")
public interface ControllerDocs {

    //일정 등록
    @Operation(summary = "일정 정보 저장", description = "할일, 작성자명, 비밀번호를 입력하여 일정을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 저장 성공"),
            @ApiResponse(responseCode = "404", description = "일정 정보 미입력으로 저장 실패") })
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto);

    //일정 전체 조회
    @Operation(summary = "전체 일정 조회", description = "작성자명, 날짜 조건에 따른 전체 일정을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 전체 조회 성공"),
            @ApiResponse(responseCode = "404", description = "일정 전체 조회 실패") })
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) String author,
                                                                      @RequestParam(required = false) String update);

}