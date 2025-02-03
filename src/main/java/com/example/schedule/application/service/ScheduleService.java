package com.example.schedule.application.service;

import com.example.schedule.application.dto.ScheduleCreateRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.application.dto.ScheduleUpdateRequestDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleCreateRequestDto dto);

    List<ScheduleResponseDto> getSchedules(Long userId, String startDate, String endDate);

    ScheduleResponseDto getSchedule(Long scheduleId);

    ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto dto);

    void deleteSchedule(Long scheduleId, String password);
}
