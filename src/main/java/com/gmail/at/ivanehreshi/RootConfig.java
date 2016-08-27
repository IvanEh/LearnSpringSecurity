package com.gmail.at.ivanehreshi;

import com.gmail.at.ivanehreshi.services.UserService;
import com.gmail.at.ivanehreshi.services.UserServiceImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.sql.DataSourceDefinition;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
public class RootConfig {

    @Bean
    DataSource dataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/test");
        mysqlDataSource.setUser("ivaneh");
        mysqlDataSource.setPassword("password");
        mysqlDataSource.setPort(3306);

        return mysqlDataSource;
    }

    @Bean
    UserService userService() {
        return new UserServiceImpl();
    }

    @Autowired
    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
