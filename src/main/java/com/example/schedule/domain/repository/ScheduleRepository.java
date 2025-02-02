package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Long userId, Schedule schedule);

    List<ScheduleResponseDto> findSchedulesById(Long userId, String startDate, String endDate);
//
//    ScheduleResponseDto findSchedule(Long id);
//
//    int updateSchedule(Long id,String password, String author, String todo);
//
//    int deleteSchedule(Long id, String password);

}
