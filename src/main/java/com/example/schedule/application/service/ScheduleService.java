package com.example.schedule.application.service;

import com.example.schedule.application.dto.ScheduleRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    //일정 생성
    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);

    //일정 전체 정보 가져오기
    List<ScheduleResponseDto> getSchedules(Long userId, String startDate, String endDate);

    //일정 단건 정보 가져오기
    ScheduleResponseDto getSchedule(Long scheduleId);

    //일정 수정
    ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto);

    //일정 삭제
    void deleteSchedule(Long scheduleId, String password);
}
