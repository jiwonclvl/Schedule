package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserResponseDto saveUser(User user) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
        insert.withTableName("users").usingGeneratedKeyColumns("user_id");

        //응답 데이터 날짜 출력 형식 변경 (YYYY-MM-DD로 변경)
        String createTimeFormat = localDateTimeFormat(user.getCreateAt());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", user.getAuthor());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getEmail());
        parameters.put("create_date", user.getCreateAt());
        parameters.put("update_date", user.getCreateAt());

        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new UserResponseDto(id.longValue(), user.getAuthor(),createTimeFormat,createTimeFormat);
    }

    @Override
    public UserResponseDto findUser(Long userId) {
        List<UserResponseDto> result = jdbcTemplate.query("select * from users where user_id = ?", userRowMapper(), userId);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."));
    }

    @Override
    public void updateUser(Long userId, String author, String password) {

        //수정일 update
        LocalDateTime update = LocalDateTime.now();

        try {
            String storedPassword = jdbcTemplate.queryForObject(
                    "select password from users where user_id = ?", String.class, userId
            );

            //비밀번호 검증
            if (!password.equals(storedPassword)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
            }

        } catch (EmptyResultDataAccessException e) {
            return;
        }

        //작성자명 수정
        jdbcTemplate.update("update users set author = ?, update_date = ? where user_id = ?", author,update, userId);
    }


    private String localDateTimeFormat(LocalDateTime create) {
        return create.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private RowMapper<UserResponseDto> userRowMapper() {
        return new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserResponseDto(
                        rs.getLong("user_id"),
                        rs.getString("author"),
                        localDateTimeFormat(rs.getTimestamp("create_date").toLocalDateTime()),
                        localDateTimeFormat(rs.getTimestamp("update_date").toLocalDateTime())
                );
            }
        };
    }
}
