package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        return new ScheduleResponseDto(id.longValue(), schedule.getAuthor(), schedule.getTodo(), createTimeFormat, createTimeFormat);
    }

    //일정 전체 조회 (조건이 없는 경우)
    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        return jdbcTemplate.query("select * from schedule order by update_date desc", scheduleRowMapper());
    }

    //일정 전체 조회 (조건이 있는 경우)
    @Override
    public List<ScheduleResponseDto> findAllSchedule(String author, String update) {


        //날짜만 입력된 경우
        if (author == null || "".equals(author)) {
            return jdbcTemplate.query("select * from schedule where  date_format(update_date, '%Y-%m-%d') = ? order by update_date desc", scheduleRowMapper(), update);
        }

        //작성자명만 입력된 경우
        if (update == null || "".equals(update)) {
            return jdbcTemplate.query("select * from schedule where  author = ? order by update_date desc", scheduleRowMapper(), author);
        }

        //둘다 입력된 경우
        return jdbcTemplate.query("select * from schedule where author = ? and date_format(update_date, '%Y-%m-%d') = ? order by update_date desc", scheduleRowMapper(), author, update);
    }

    //일정 단건 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        List<ScheduleResponseDto> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id= " + id));
    }

    //일정 수정하기
    @Override
    public int updateSchedule(Long id, String todo, String author, String password) {

        //수정일 변경
        LocalDateTime update = LocalDateTime.now();

        //할 일만 수정 경우 (아이디와 비밀번호가 일치하는 경우에만 변경)
        if (author == null || "".equals(author)) {
            return jdbcTemplate.update("update schedule set todo = ?, update_date = ? where id = ? and password = ? ", todo,update, id, password);
        }
        //작성자명만 수정하는 경우 (아이디와 비밀번호가 일치하는 경우에만 변경)
        if (todo == null || "".equals(todo)) {
            return jdbcTemplate.update("update schedule set author = ? , update_date = ? where id = ? and password = ? ", author,update, id, password);
        }

        return jdbcTemplate.update("update schedule set author = ?, todo = ? , update_date = ? where id = ? and password = ? ", author, todo,update, id, password);
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
