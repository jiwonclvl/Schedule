package com.example.schedule.docs;


import com.example.schedule.application.dto.ScheduleRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "일정 관리", description = "일정 관리 API입니다.")
public interface ScheduleControllerDocs {

    @Operation(summary = "일정 정보 저장", description = "할일, 작성자명, 비밀번호를 입력하여 일정을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 저장 성공"),
            @ApiResponse(responseCode = "404", description = "작성자명 및 비밀번호 미입력으로 저장 실패") })
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @PathVariable Long userId,
            @RequestBody ScheduleRequestDto dto);

//    @Operation(summary = "일정 전체 조회", description = "작성자명, 날짜 조건에 따른 전체 일정을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "일정 전체 조회 성공"),
//            @ApiResponse(responseCode = "400", description = "일정 전체 조회 실패") })
//    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
//            @RequestParam(required = false) String author,
//            @RequestParam(required = false) String update
//    );
//
//    @Operation(summary = "일정 단건 조회", description = "고유 식별자 id를 통해 하나의 일정을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "일정 단건 조회 성공"),
//            @ApiResponse(responseCode = "400", description = "일정 단건 조회 실패") })
//    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id);
//
//    @Operation(summary = "일정 수정", description = "작성자명, 할일만 수정 가능하며, 사용자가 올바른 비밀번호를 입력했을 때만 일정을 수정합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "일정 수정 성공"),
//            @ApiResponse(responseCode = "400", description = "존재하지 않은 일정 혹은 비밀번호 불일치로 수정 실패"),
//            @ApiResponse(responseCode = "404", description = "작성자명과 할 일 모두 미입력으로 수정 실패")
//    })
//    public ResponseEntity<ScheduleResponseDto> updateSchedule(
//            @PathVariable Long id,
//            @RequestBody ScheduleRequestDto dto
//    );
//
//    @Operation(summary = "일정 삭제", description = "고유 식별자 id를 통해 하나의 일정을 삭제할 수 있으며, 사용자가 올바른 비밀번호를 입력했을 때만 일정을 수정할 수 있습니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "일정 삭제 성공"),
//            @ApiResponse(responseCode = "400", description = "존재하지 않은 일정 혹은 비밀번호 불일치로 삭제 실패")})
//    public ResponseEntity<Void> deleteSchedule(
//            @PathVariable Long id,
//            @RequestBody String password
//    );
}