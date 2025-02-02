package com.example.schedule.application.service;

import com.example.schedule.application.dto.ScheduleRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;
import com.example.schedule.domain.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getUserId(), dto.getTodo());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getSchedules(Long userId, String startDate, String endDate) {

        return scheduleRepository.findSchedulesByUserId(userId, startDate, endDate);
    }

    @Override
    public ScheduleResponseDto getSchedule(Long scheduleId) {

        return scheduleRepository.findScheduleByScheduleId(scheduleId);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto) {

        int update = scheduleRepository.updateSchedule(scheduleId, dto.getUserId(), dto.getPassword(), dto.getTodo());

        if(update == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다.");
        }

        return scheduleRepository.findScheduleByScheduleId(scheduleId);
    }

//    @Transactional
//    @Override
//    public void deleteSchedule(Long id, String password) {
//
//        int delete = scheduleRepository.deleteSchedule(id, password);
//
//        if (delete == 0){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다.");
//        }
//    }

    private boolean isEmpty(String value1, String value2) {
        return !StringUtils.hasText(value1) && ! StringUtils.hasText(value2);
    }
}
