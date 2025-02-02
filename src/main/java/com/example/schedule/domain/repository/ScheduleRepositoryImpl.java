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

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Long userId, Schedule schedule) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);

        insert.withTableName("schedules").usingGeneratedKeyColumns("schedule_id");

        //사용자에게 보여줄 날짜 출력 형식 변경 (YYYY-MM-DD로 변경)
         String createTimeFormat = localDateTimeFormat(schedule.getCreateAt());

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("todo", schedule.getTodo());
        params.put("create_date", schedule.getCreateAt());
        params.put("update_date", schedule.getUpdateAt());

        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new ScheduleResponseDto(id.longValue(), userId, schedule.getTodo(), createTimeFormat, createTimeFormat);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesById(Long userId, String startDate, String endDate) {

        String sql = "select * from schedules where user_id = ?";
        List<ScheduleResponseDto> result = jdbcTemplate.query(sql, scheduleRowMapper(), userId);

        //일정이 없는 경우 예외처리
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"일정이 존재하지 않습니다.");
        }

        //조건이 존재하지 않을 때
        if (isEmpty(startDate, endDate)) {
            sql += " order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(), userId);
        }
        //endDate 조건이 있는 경우
        if (!StringUtils.hasText(startDate)) {
            sql += " and date_format(update_date, '%Y-%m-%d')  <= ? order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(),userId, endDate);
        }
        //startDate 조건이 있는 경우
        if (!StringUtils.hasText(endDate)) {
            sql += " and date_format(update_date, '%Y-%m-%d') >= ? order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(),userId, startDate);
        }

        sql += " and date_format(update_date, '%Y-%m-%d') between ? and ? order by update_date desc";
        return jdbcTemplate.query(sql, scheduleRowMapper(),userId, startDate, endDate);
    }

//    @Override
//    public ScheduleResponseDto findSchedule(Long id) {
//        List<ScheduleResponseDto> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);
//        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));
//    }
//
//    @Override
//    public int updateSchedule(Long id, String password, String author, String todo) {
//
//        //TODO: 비밀번호 검증 따로 빼기
//        LocalDateTime update = LocalDateTime.now();
//        String sql = "update schedule";
//
//        if(!StringUtils.hasText(author)) {
//            sql += " set todo = ?, update_date = ? where id = ? and password = ?";
//            return jdbcTemplate.update(sql, todo, update, id, password);
//        }
//        if(!StringUtils.hasText (todo)) {
//            sql += " set author = ?, update_date = ? where id = ? and password = ?";
//            return jdbcTemplate.update(sql, author, update, id, password);
//        }
//
//        sql += " set author = ?, todo = ? , update_date = ? where id = ? and password = ?";
//        return jdbcTemplate.update(sql, author, todo,update, id, password);
//
//    }
//
//    //일정 삭제하기
//    @Override
//    public int deleteSchedule(Long id, String password) {
//        //TODO: 비밀번호 검증 따로 빼기
//        return jdbcTemplate.update("delete from schedule where id = ? and password = ?", id, password);
//    }

    //날짜 출력 형식 변경
    private String localDateTimeFormat(LocalDateTime create) {
        return create.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("schedule_id"),
                        rs.getLong("user_id"),
                        rs.getString("todo"),
                        localDateTimeFormat(rs.getTimestamp("create_date").toLocalDateTime()),
                        localDateTimeFormat(rs.getTimestamp("update_date").toLocalDateTime())
                );
            }
        };
    }

    private boolean isEmpty(String value1, String value2) {
        return !StringUtils.hasText(value1) && ! StringUtils.hasText(value2);
    }

}
