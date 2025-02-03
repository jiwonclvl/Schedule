package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);

        insert.withTableName("schedules").usingGeneratedKeyColumns("schedule_id");

        //사용자에게 보여줄 날짜 출력 형식 변경 (YYYY-MM-DD로 변경)
        String createTimeFormat = localDateTimeFormat(schedule.getCreateAt());

        System.out.println("UserId = " + schedule.getUserId());

        //입력한 user_id가 존재하지 않는 경우
        String sql = "SELECT user_id FROM users WHERE user_id = ?";
        List<Long> userId = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("user_id"), schedule.getUserId());

        if (userId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", schedule.getUserId());
        params.put("todo", schedule.getTodo());
        params.put("create_date", schedule.getCreateAt());
        params.put("update_date", schedule.getUpdateAt());

        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new ScheduleResponseDto(id.longValue(), schedule.getUserId(), schedule.getTodo(), createTimeFormat, createTimeFormat);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByUserId(Long userId, String startDate, String endDate) {

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

    @Override
    public ScheduleResponseDto findScheduleByScheduleId(Long scheduleId) {
        List<ScheduleResponseDto> result = jdbcTemplate.query(
                "select * from schedules where schedule_id = ?", scheduleRowMapper(), scheduleId
        );
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));
    }

    @Override
    public int updateSchedule(Long scheduleId, Long userId, String password, String todo) {

        LocalDateTime update = LocalDateTime.now();
        String storedPassword = jdbcTemplate.queryForObject(
                "SELECT password FROM users WHERE user_id = ?", String.class, userId
        );

        System.out.println("Password = " + password);
        System.out.println("storedPassword = " + storedPassword);

        //비밀번호 검증
        if(!password.equals(storedPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

        //할일 수정하기
        return jdbcTemplate.update(
                "update schedules set todo = ?, update_date = ? where schedule_id = ?", todo, update, scheduleId
        );
    }

    //일정 삭제하기
    @Override
    public int deleteSchedule(Long scheduleId, Long userId, String password) {

        String storedPassword = jdbcTemplate.queryForObject(
                "SELECT password FROM users WHERE user_id = ?", String.class, userId
        );

        //비밀번호 검증
        if(!password.equals(storedPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

        return jdbcTemplate.update("delete from schedules where schedule_id = ? and user_id= ?", scheduleId,userId);
    }

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
