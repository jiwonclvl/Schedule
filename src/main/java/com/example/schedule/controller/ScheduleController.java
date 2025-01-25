package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    //빈 MAP 배열 자료구조 생성
   private final Map<Long, Schedule> scheduleList = new HashMap<>();

   //일정 생성하기
   @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

       //id 1씩 증가
       Long scheduleId = scheduleList.isEmpty() ? 1: Collections.max(scheduleList.keySet()) +1;

       //아이디, 할일, 작성자명, 비밀번호, 작성일, 생성일 Map에 저장 --> 나중에 데이터 베이스에 저장해야함
       Schedule schedule = new Schedule(scheduleId, dto.getTodo(), dto.getAuthor(), dto.getPassword(), dto.getCreateAt(),dto.getCreateAt());
       scheduleList.put(scheduleId, schedule);

       return new ResponseEntity<>(new ScheduleResponseDto(schedule),HttpStatus.CREATED);
   }

   //전체 일정 조회하기
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
       List<ScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();

       //배열에 추가
       for(Schedule schedule : scheduleList.values()) {
           scheduleResponseDtoList.add(new ScheduleResponseDto(schedule));
       }

       return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }


}
