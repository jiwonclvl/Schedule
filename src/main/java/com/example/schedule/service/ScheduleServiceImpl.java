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
        //전달 받은 데이터를 통해 일정 객체 생성
        Schedule schedule = new Schedule(dto.getAuthor(), dto.getPassword(), dto.getTodo());

        return scheduleRepository.saveSchedule(schedule);
    }

    //일정 전체 조회하기
    @Override
    public List<ScheduleResponseDto> findAllSchedule(String author, String update) {

        //둘 다 입력하지 않은 경우
        if ("".equals(author) && "".equals(update)) {
            return scheduleRepository.findAllSchedule();
        }

        //날짜만 입력된 경우
        if ( "".equals(author)) {
            return scheduleRepository.findAllScheduleByUpdate(update);
        }

        //작성자명만 입력된 경우
        if ("".equals(update)) {
            return scheduleRepository.findAllScheduleByAuthor(author);
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
        int update = 0;

        //둘 다 입력하지 않은 경우
        if (dto.getTodo() == null && dto.getAuthor() == null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        //할 일만 수정 경우 (아이디와 비밀번호가 일치하는 경우에만 변경)
        if (dto.getAuthor() == null) {
            update = scheduleRepository.updateScheduleTodo(id, dto.getTodo(), dto.getPassword());
        }
        //작성자명만 수정하는 경우 (아이디와 비밀번호가 일치하는 경우에만 변경)
        if (dto.getTodo() == null) {
            update = scheduleRepository.updateScheduleAuthor(id, dto.getAuthor(), dto.getPassword());
        }
        //둘 다 수정하는 경우
        if(dto.getAuthor() != null && dto.getTodo() != null) {
            update = scheduleRepository.updateSchedule(id, dto.getTodo(), dto.getAuthor(), dto.getPassword());
        }

        //쿼리를 0번 적용했다면 id가 없다는 의미
        if(update == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return scheduleRepository.findScheduleById(id);
    }

    //일정 삭제하기
    @Override
    public void deleteSchedule(Long id) {
        int delete = scheduleRepository.deleteSchedule(id);
        if(delete == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }


}
