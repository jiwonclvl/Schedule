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

        if (!StringUtils.hasText(dto.getAuthor()) || !StringUtils.hasText(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자명과 비밀번호는 반드시 입력되어야 합니다.");
        }

        Schedule schedule = new Schedule(dto.getAuthor(), dto.getPassword(), dto.getTodo());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getSchedules(String author, String update) {

        return scheduleRepository.findSchedules(author, update);
    }

    @Override
    public ScheduleResponseDto getSchedule(Long id) {

        return scheduleRepository.findSchedule(id);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        if (isEmpty(dto.getTodo(), dto.getAuthor())) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자명과 할 일 중 하나 이상을 입력해야 합니다.");
        }

        int update = scheduleRepository.updateSchedule(id, dto.getPassword(), dto.getAuthor(), dto.getTodo());

        if(update == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다.");
        }

        return scheduleRepository.findSchedule(id);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, String password) {

        int delete = scheduleRepository.deleteSchedule(id, password);

        if (delete == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다.");
        }
    }

    private boolean isEmpty(String value1, String value2) {
        return !StringUtils.hasText(value1) && ! StringUtils.hasText(value2);
    }
}
