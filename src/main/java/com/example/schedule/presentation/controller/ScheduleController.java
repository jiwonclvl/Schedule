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
    @PostMapping()
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto) {

       return new ResponseEntity<>(scheduleService.createSchedule(dto),HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @PathVariable Long userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {

       return new ResponseEntity<>(scheduleService.getSchedules(userId, startDate, endDate), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId
    ) {

        return new ResponseEntity<>(scheduleService.getSchedule(scheduleId), HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto),HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto
    ) {

        scheduleService.deleteSchedule(scheduleId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
