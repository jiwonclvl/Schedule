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
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

       return new ResponseEntity<>(scheduleService.createSchedule(dto),HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String update
    ) {

       return new ResponseEntity<>(scheduleService.getSchedules(author, update), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.getSchedule(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody String password
    ) {

        scheduleService.deleteSchedule(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
