package com.example.schedule.application.service;

import com.example.schedule.application.dto.ScheduleCreateRequestDto;
import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.application.dto.ScheduleUpdateRequestDto;
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
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto dto) {

        //사용자 아이디를 입력하지 않은 경우
        if (dto.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 아이디는 반드시 입력되어야 합니다.");
        }

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
    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto dto) {

        //비밀번호를 입력하지 않은 경우
        if (!StringUtils.hasText(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 반드시 입력되어야 합니다.");
        }

        scheduleRepository.updateSchedule(scheduleId, dto.getPassword(), dto.getTodo());

        return scheduleRepository.findScheduleByScheduleId(scheduleId);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId, String password) {

        scheduleRepository.deleteSchedule(scheduleId, password);

    }

}
