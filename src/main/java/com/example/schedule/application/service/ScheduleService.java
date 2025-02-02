package com.example.schedule.application.service;

import com.example.schedule.application.dto.ScheduleRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> getSchedules(Long userId, String startDate, String endDate);

    ScheduleResponseDto getSchedule(Long scheduleId);

//    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);

//    void deleteSchedule(Long id, String password);
}
