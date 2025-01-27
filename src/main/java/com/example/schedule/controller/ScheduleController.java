package com.example.schedule.controller;

import com.example.schedule.docs.ControllerDocs;
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
public class ScheduleController implements ControllerDocs {

    //필드
    private final ScheduleService scheduleService;

    //생성자
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

   //일정 생성하기
   @Override
   @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

       return new ResponseEntity<>(scheduleService.saveSchedule(dto),HttpStatus.CREATED);
   }

    //전체 일정 조회하기
    @Override
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String update
    ) {
       return new ResponseEntity<>(scheduleService.findAllSchedule(author, update), HttpStatus.OK);
    }

    //일정 단건 조회하기
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    //일정 수정하기
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto),HttpStatus.OK);
    }

    //일정 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
