package com.example.schedule.domain.repository;

import com.example.schedule.application.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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

        //사용자에게 보여줄 날짜 출력 형식 변경 (YYYY-MM-DD로 변경)
        String createTimeFormat = localDateTimeFormat(user.getCreateAt());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", user.getAuthor());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getEmail());
        parameters.put("create_date", user.getEmail());
        parameters.put("update_date", user.getEmail());

        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new UserResponseDto(id.longValue(), user.getAuthor(),createTimeFormat,createTimeFormat);
    }

    //날짜 출력 형식 변경
    private String localDateTimeFormat(LocalDateTime create) {
        return create.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
