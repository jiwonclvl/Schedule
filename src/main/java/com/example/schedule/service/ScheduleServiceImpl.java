package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        //전달 받은 데이터를 통해 일정 생성
        Schedule schedule = new Schedule(dto.getTodo(), dto.getAuthor(), dto.getPassword());

        //데이터 저장하기
        return scheduleRepository.saveSchedule(schedule);

    }

    //일정 전체 조회하기
    @Override
    public List<ScheduleResponseDto> findAllSchedule(String author, String update) {

        //둘 다 입력하지 않은 경우
        if ("".equals(author) && "".equals(update)) {
            return scheduleRepository.findAllSchedule();
        }

        return scheduleRepository.findAllSchedule(author, update);
    }

    //일정 단건 조회하기
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }

    //일정 수정하기
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        //둘 다 입력하지 않은 경우
        if (dto.getTodo() == null && dto.getAuthor() == null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        int update = scheduleRepository.updateSchedule(id, dto.getTodo(), dto.getAuthor(), dto.getPassword());

        //쿼리를 0번 적용했다면 id가 없다는 의미
        if(update == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return scheduleRepository.findScheduleById(id);
    }

//    @Override
//    public ScheduleResponseDto deleteSchedule(Long id) {
//        return null;
//    }


}
