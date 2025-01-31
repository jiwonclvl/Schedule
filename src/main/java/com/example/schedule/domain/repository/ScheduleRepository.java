package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    //일정 저장하기
    ScheduleResponseDto saveSchedule(Schedule schedule);

    //일정 전체 조회하기
    List<ScheduleResponseDto> findAllSchedules(String author, String update);

    //일정 단건 조회하기
    ScheduleResponseDto findScheduleById(Long id);

    //일정 수정하기(
    int updateSchedule(Long id,String password, String author, String todo);

    //일정 삭제하기
    int deleteSchedule(Long id);

}
