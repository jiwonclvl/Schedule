package com.example.schedule.application.service;

import com.example.schedule.application.dto.ScheduleRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(Long userId, ScheduleRequestDto dto);

//    List<ScheduleResponseDto> getSchedules(String author, String update);
//
//    ScheduleResponseDto getSchedule(Long id);

//    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);

//    void deleteSchedule(Long id, String password);
}
