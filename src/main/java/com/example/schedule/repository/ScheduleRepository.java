package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    //일정 저장하기
    ScheduleResponseDto saveSchedule(Schedule schedule);

    //일정 전체 조회하기(입력이 없는 경우)
    List<ScheduleResponseDto> findAllSchedule();

    //일정 전체 조회하기(날짜만 입력한 경우)
    List<ScheduleResponseDto> findAllScheduleByUpdate(String update);

    //일정 전체 조회하기(작성자만 입력한 경우)
    List<ScheduleResponseDto> findAllScheduleByAuthor(String author);

    //일정 전체 조회하기(모두 입력한 경우)
    List<ScheduleResponseDto> findAllSchedule(String author, String update);

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
