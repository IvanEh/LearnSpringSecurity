package com.gmail.at.ivanehreshi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.List;

public class UserServiceImpl implements UserService {
    public final String SQL_INSERT_USER =
            "INSERT INTO `user`(username, password, enabled) VALUES (?, ?, ?)";
    public final String SQL_INSERT_USER_ROLE =
            "INSERT INTO `user_role`(username, role) VALUES (?, ?)";
    public final String SQL_SELECT_USER =
            "SELECT username, password, enabled FROM `user` WHERE username = ?";
    public final String SQL_SELECT_USER_ROLES =
            "SELECT username, role FROM `user_role` WHERE username = ?";
    JdbcUserDetailsManager userDetailsManager;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userDetailsManager = new JdbcUserDetailsManager();
    }

    @Override
    public boolean save(UserDetails user) {
        userDetailsManager.createUser(user);

        for (GrantedAuthority authority : user.getAuthorities()) {
            jdbcTemplate.update(SQL_INSERT_USER_ROLE, user.getUsername(), authority.getAuthority());
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);

        List<GrantedAuthority> authorities = jdbcTemplate.query(SQL_SELECT_USER_ROLES, (rs, rowNum) -> {
            return new SimpleGrantedAuthority(rs.getString(2));
        }, username);

        return new User(userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.isEnabled(),
                        userDetails.isAccountNonExpired(),
                        userDetails.isCredentialsNonExpired(),
                        userDetails.isAccountNonLocked(),
                        authorities);
    }
}
