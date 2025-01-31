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

    //필드
    private final ScheduleRepository scheduleRepository;

    //생성자
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //일정 생성하기
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        //전달 받은 데이터를 통해 일정 객체 생성
        Schedule schedule = new Schedule(dto.getAuthor(), dto.getPassword(), dto.getTodo());

        return scheduleRepository.saveSchedule(schedule);
    }

    //일정 전체 조회하기
    @Override
    public List<ScheduleResponseDto> findAllSchedules(String author, String update) {

        return scheduleRepository.findAllSchedules(author, update);
    }

    //일정 단건 조회하기
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }

    //일정 수정
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        //둘 다 입력하지 않은 경우
        if (!StringUtils.hasText(dto.getTodo()) && !StringUtils.hasText(dto.getAuthor())) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        int update = scheduleRepository.updateSchedule(id, dto.getPassword(), dto.getAuthor(), dto.getTodo());

        //쿼리를 0번 적용했다면 id가 없다는 의미
        if(update == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return scheduleRepository.findScheduleById(id);
    }

    //일정 삭제하기
    @Override
    public void deleteSchedule(Long id, String password) {
        int delete = scheduleRepository.deleteSchedule(id, password);
        if(delete == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
