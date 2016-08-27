package com.gmail.at.ivanehreshi;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
