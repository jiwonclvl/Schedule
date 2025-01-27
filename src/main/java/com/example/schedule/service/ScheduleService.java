package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

public interface ScheduleService {
    //일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
}
