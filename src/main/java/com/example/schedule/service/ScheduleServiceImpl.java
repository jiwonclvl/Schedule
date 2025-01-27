package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    //필드
    private ScheduleRepository scheduleRepository;

    //생성자
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //일정 생성하기
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        //전달 받은 데이터를 통해 일정 생성
        Schedule schedule = new Schedule(dto.getTodo(), dto.getAuthor(), dto.getPassword());

        //데이터 저장하기
        return scheduleRepository.saveSchedule(schedule);

    }
}
