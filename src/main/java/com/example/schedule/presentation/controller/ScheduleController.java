package com.example.schedule.presentation.controller;

import com.example.schedule.docs.ScheduleControllerDocs;
import com.example.schedule.application.dto.ScheduleRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.application.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController implements ScheduleControllerDocs {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    @PostMapping("/{userId}")
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @PathVariable Long userId,
            @RequestBody ScheduleRequestDto dto) {

       return new ResponseEntity<>(scheduleService.createSchedule(userId, dto),HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @PathVariable Long userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {

       return new ResponseEntity<>(scheduleService.getSchedules(userId, startDate, endDate), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
//
//        return new ResponseEntity<>(scheduleService.getSchedule(id), HttpStatus.OK);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<ScheduleResponseDto> updateSchedule(
//            @PathVariable Long id,
//            @RequestBody ScheduleRequestDto dto
//    ) {
//
//        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto),HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSchedule(
//            @PathVariable Long id,
//            @RequestBody String password
//    ) {
//
//        scheduleService.deleteSchedule(id, password);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
