package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    //일정 저장하기
    ScheduleResponseDto saveSchedule(Schedule schedule);

    //일정 전체 조회하기
    List<ScheduleResponseDto> findAllSchedule();
    List<ScheduleResponseDto> findAllSchedule(String author, String update);

    //일정 단건 조회하기
    ScheduleResponseDto findScheduleById(Long id);

    //일정 수정하기
}
