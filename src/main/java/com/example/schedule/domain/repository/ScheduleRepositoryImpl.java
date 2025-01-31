package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    //템플릿 생성 (필드)
    private final JdbcTemplate jdbcTemplate;

    //생성자
    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //일정 생성하기
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        //데이터베이스에 데이터 저장 시 사용
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);

        //key = id인 schedule 테이블에 저장
        insert.withTableName("schedule").usingGeneratedKeyColumns("id");

        //사용자에게 보여줄 날짜 출력 형식 변경 (YYYY-MM-DD로 변경)
        String createTimeFormat = localDateTimeFormat(schedule.getCreateAt());

        //배열에 값 넣기
        Map<String, Object> params = new HashMap<>();
        params.put("author", schedule.getAuthor());
        params.put("password", schedule.getPassword());
        params.put("todo", schedule.getTodo());
        params.put("create_date", schedule.getCreateAt());
        params.put("update_date", schedule.getUpdateAt());

        //저장
        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new ScheduleResponseDto(id.longValue(), schedule.getAuthor(), schedule.getTodo(), createTimeFormat, createTimeFormat);
    }

    //일정 전체 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules(String author, String update) {
        //기본 베이스 쿼리
        String sql = "select * from schedule";

        //입력이 아무것도 없는 경우
        if (!StringUtils.hasText (author) && !StringUtils.hasText (update)) {
            return jdbcTemplate.query(sql, scheduleRowMapper());
        }
        //날짜만 입력된 경우
        if(!StringUtils.hasText(author)) {
            sql += " where date_format(update_date, '%Y-%m-%d')  = ? order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(), update);
        }
        //작성자명만 입력된 경우
        if(!StringUtils.hasText(update)) {
            sql += " where author = ? order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(), author);
        }

        //둘 다 입력한 경우
        sql += " where author = ? or date_format(update_date, '%Y-%m-%d')  = ? order by update_date desc";
        return jdbcTemplate.query(sql, scheduleRowMapper(),author, update);
    }

    //일정 단건 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        List<ScheduleResponseDto> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id= " + id));
    }

    //일정 수정하기
    @Override
    public int updateSchedule(Long id, String password, String author, String todo) {
        //수정일 변경
        LocalDateTime update = LocalDateTime.now();
        String sql = "update schedule";

        //할일만 입력된 경우
        if(!StringUtils.hasText(author)) {
            sql += " set todo = ?, update_date = ? where id = ? and password = ?";
            return jdbcTemplate.update(sql, todo, update, id, password);
        }
        //작성자명만 입력된 경우
        if(!StringUtils.hasText (todo)) {
            sql += " set author = ?, update_date = ? where id = ? and password = ?";
            return jdbcTemplate.update(sql, author, update, id, password);
        }

        sql += " set author = ?, todo = ? , update_date = ? where id = ? and password = ?";
        return jdbcTemplate.update(sql, author, todo,update, id, password);

    }

    //일정 삭제하기
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    //날짜 출력 형식 변경
    private String localDateTimeFormat(LocalDateTime create) {
        return create.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    //List 형태로 만들기
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("todo"),
                        rs.getTimestamp("create_date").toLocalDateTime().toString(),
                        rs.getTimestamp("update_date").toLocalDateTime().toString()
                );
            }
        };
    }

}
