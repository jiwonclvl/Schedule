package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

public interface ScheduleRepository {
    //일정 저장하기
    ScheduleResponseDto saveSchedule(Schedule schedule);
}
