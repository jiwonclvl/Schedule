package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    //일정 등록
    ScheduleResponseDto saveSchedule(Schedule schedule);

    //일정 전체 조회
    List<ScheduleResponseDto> findSchedulesByUserId(Long userId, String startDate, String endDate);

    //일정 단건 조회
    ScheduleResponseDto findScheduleByScheduleId(Long scheduleId);

    //일정 수정
    void updateSchedule(Long scheduleId, String password, String todo);

    //일정 삭제
    void deleteSchedule(Long scheduleId, String password);

}
