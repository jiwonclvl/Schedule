package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    //템플릿 생성 (필드)
    private final JdbcTemplate jdbcTemplate;

    //생성자
    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        //데이터 저장 시 사용
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);

        //schedule 데이블에 저장 && key = id
        insert.withTableName("schedule").usingGeneratedKeyColumns("id");

        //DB에 넣을 형식으로 변환
        Timestamp create = Timestamp.valueOf(schedule.getCreateAt());
        Timestamp update = Timestamp.valueOf(schedule.getUpdateAt());

        //날짜 출력 형식 변경
        String createTimeFormat = localDateTimeFormat(schedule.getCreateAt());


        //배열에 값 넣기
        Map<String, Object> params = new HashMap<>();
        params.put("author", schedule.getAuthor());
        params.put("password", schedule.getPassword());
        params.put("todo", schedule.getTodo());
        params.put("create_date", create);
        params.put("update_date", update);

        //저장
        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new ScheduleResponseDto(id.longValue(), schedule.getAuthor(), schedule.getTodo(),createTimeFormat,createTimeFormat);
    }

    //날짜 출력 형식 변경
    private String localDateTimeFormat(LocalDateTime create) {
        return create.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
