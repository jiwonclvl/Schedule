package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    //필드
    private final ScheduleService scheduleService;

    //생성자
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

   //일정 생성하기
   @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

       return new ResponseEntity<>(scheduleService.saveSchedule(dto),HttpStatus.CREATED);
   }

//   //전체 일정 조회하기
//    @GetMapping
//    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
////       List<ScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();
////
////       //배열에 추가
////       for(Schedule schedule : scheduleList.values()) {
////           scheduleResponseDtoList.add(new ScheduleResponseDto(schedule));
////       }
////
////       return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
//    }


}
