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

        //응답 데이터 날짜 출력 형식 변경 (YYYY-MM-DD로 변경)
        String createTimeFormat = localDateTimeFormat(schedule.getCreateAt());

        //작성자 ID가 존재하지 않는 경우
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

        //기본 베이스 query
        String sql = "select * from schedules where user_id = ?";

        //날짜 조건이 입력되지 않은 경우 -> 전체 일정 조회
        if (isEmpty(startDate, endDate)) {
            sql += " order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(), userId);
        }
        //endDate(마지막 날짜) 조건만 있는 경우 -> ~ endDate 일정 조회
        if (!StringUtils.hasText(startDate)) {
            sql += " and date_format(update_date, '%Y-%m-%d')  <= ? order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(),userId, endDate);
        }
        //startDate(시작 날짜) 조건만 있는 경우 -> startDate ~ 일정 조회
        if (!StringUtils.hasText(endDate)) {
            sql += " and date_format(update_date, '%Y-%m-%d') >= ? order by update_date desc";
            return jdbcTemplate.query(sql, scheduleRowMapper(),userId, startDate);
        }

        //startDate, endDate 조건 모두 있는 경우 -> startDate ~ endDate 일정 조회
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
    public void updateSchedule(Long scheduleId, String password, String todo) {

        //수정일 update
        LocalDateTime update = LocalDateTime.now();

        String sql = "SELECT u.password FROM users u INNER JOIN schedules s on u.user_id = s.user_id  where schedule_id = ?";

        try {
            String storedPassword = jdbcTemplate.queryForObject(
                    sql, String.class, scheduleId
            );

            //비밀번호 검증
            if (!password.equals(storedPassword)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
            }

        } catch (EmptyResultDataAccessException e) {
            return;
        }

        //일정의 `할 일(todo)`수정
        jdbcTemplate.update(
                "update schedules set todo = ?, update_date = ? where schedule_id = ?", todo, update, scheduleId
        );
    }

    @Override
    public void deleteSchedule(Long scheduleId, String password) {

        String sql = "SELECT u.password FROM users u INNER JOIN schedules s on u.user_id = s.user_id  where schedule_id = ?";

        try {
            String storedPassword = jdbcTemplate.queryForObject(
                    sql, String.class, scheduleId
            );

            //비밀번호 검증
            if (!password.equals(storedPassword)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
            }

        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다.");
        }

        jdbcTemplate.update("delete from schedules where schedule_id = ?", scheduleId);
    }

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
