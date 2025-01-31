package com.example.schedule.application.service;

import com.example.schedule.application.dto.ScheduleRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    //일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    //일정 전체 조회
    List<ScheduleResponseDto> findAllSchedules(String author, String update);

    //일정 단건 조회
    ScheduleResponseDto findScheduleById(Long id);

    //일정 수정하기
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);

    //일정 삭제하기
    void deleteSchedule(Long id);
}
