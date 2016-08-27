package com.gmail.at.ivanehreshi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {
    public final String SQL_INSERT_USER =
            "INSERT INTO `user`(username, password, enabled) VALUES (?, ?, TRUE)";
    public final String SQL_INSERT_USER_ROLE =
            "INSERT INTO `user_role`(username, role) VALUES (?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(UserDetails user) {
        jdbcTemplate.update(SQL_INSERT_USER, user.getUsername(), user.getPassword());

        for (GrantedAuthority authority : user.getAuthorities()) {
            jdbcTemplate.update(SQL_INSERT_USER_ROLE, user.getUsername(), authority.getAuthority());
        }

        return true;
    }
}
