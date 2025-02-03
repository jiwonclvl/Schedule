package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findSchedulesByUserId(Long userId, String startDate, String endDate);

    ScheduleResponseDto findScheduleByScheduleId(Long scheduleId);

    void updateSchedule(Long scheduleId, String password, String todo);

    void deleteSchedule(Long scheduleId, String password);

}
