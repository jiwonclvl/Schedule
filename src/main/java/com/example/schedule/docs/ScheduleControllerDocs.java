package com.example.schedule.docs;

import com.example.schedule.application.dto.ScheduleCreateRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.application.dto.ScheduleUpdateRequestDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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

    @Operation(summary = "일정 등록", description = "사용자 ID와 할 일을 입력하여 일정을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 ID로 인해 일정 등록에 실패하였습니다."),
            @ApiResponse(responseCode = "400", description = "사용자 ID 미입력으로 일정 등록에 실패하였습니다.")
    })
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Parameter(description = "일정 등록 요청 데이터")
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "일정 등록 시 필요한 데이터", content = @Content(mediaType = "application/json"))
            ScheduleCreateRequestDto dto
    );

    @Operation(summary = "일정 전체 조회", description = "특정 사용자의 전체 일정 목록을 조회합니다. 시작 날짜(startDate)와 종료 날짜(endDate)를 지정하면 해당 기간의 일정만 반환됩니다. 날짜를 입력하지 않으면 전체 일정이 조회됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 전체 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 ID이거나, 해당 기간에 일정이 없습니다.")
    })
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @Parameter(description = "조회할 사용자 ID")
            @PathVariable Long userId,

            @Parameter(description = "조회할 일정의 시작 날짜 (형식: yyyy-MM-dd)", example = "2024-02-01")
            @RequestParam(required = false) String startDate,

            @Parameter(description = "조회할 일정의 종료 날짜 (형식: yyyy-MM-dd)", example = "2024-02-10")
            @RequestParam(required = false) String endDate
    );

    @Operation(summary = "일정 단건 조회", description = "일정 ID를 통해 일정을 단건 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 단건 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "일정 ID에 해당하는 일정이 존재하지 않습니다.")
    })
    public ResponseEntity<ScheduleResponseDto> getSchedule(
            @Parameter(description = "조회할 일정 ID", example = "1")
            @PathVariable Long scheduleId
    );

    @Operation(summary = "일정 수정", description = "일정의 `할 일(todo)`만 수정 가능하며, 올바른 비밀번호 입력시 일정 수정이 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "비밀번호 미입력으로 일정 수정에 실패하였습니다."),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "일정 ID에 해당하는 일정이 존재하지 않습니다.")
    })
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @Parameter(description = "수정할 일정 ID")
            @PathVariable Long scheduleId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "일정 수정 시 필요한 데이터", content = @Content(mediaType = "application/json"))
            @RequestBody ScheduleUpdateRequestDto dto
    );

    @Operation(summary = "일정 삭제", description = "일정 ID를 통해 일정을 삭제할 수 있으며, 올바른 비밀번호 입력시 일정 삭제가 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "비밀번호 미입력으로 일정 삭제에 실패하였습니다."),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "일정 ID에 해당하는 일정이 존재하지 않습니다.")
    })
    public ResponseEntity<Void> deleteSchedule(
            @Parameter(description = "삭제할 일정 ID", example = "1")
            @PathVariable Long scheduleId,

            @Parameter(description = "삭제할 일정의 비밀번호", example = "1234")
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "삭제할 일정의 비밀번호 (plain text로 전달)", required = true, content = @Content(mediaType = "text/plain"))
            String password
    );
}