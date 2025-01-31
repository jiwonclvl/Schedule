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

    //일정 수정하기(할일만)
    int updateScheduleTodo(Long id, String todo,String password);

    //일정 수정하기(작성자명만)
    int updateScheduleAuthor(Long id, String author,String password);

    //둘 다 수정하기
    int updateSchedule(Long id, String todo, String author, String password);

    //일정 삭제하기
    int deleteSchedule(Long id);

}
