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

    @Operation(summary = "일정 정보 저장", description = "일정을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 저장 성공"),
            @ApiResponse(responseCode = "405", description = "일정 저장 실패(정보 기입 X)") })
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto);

    @Operation(summary = "전체 일정 조회", description = "전체 일정을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 전체 조회 성공"),
            @ApiResponse(responseCode = "400", description = "일정 전체 조회 실패") })
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) String author,
                                                                      @RequestParam(required = false) String update);

}