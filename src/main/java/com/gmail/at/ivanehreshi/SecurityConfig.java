package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        /**
         * Note that spring assumes a default db scheme with `users` and `authorities` tables.
         * We are using our own scheme so insertion(withUser()) won't work
         *
         * Manually create the scheme and add a user for testing
         */
        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("select username, role from user_role where username = ?")
                .usersByUsernameQuery("select username,password,enabled from user where username = ?");
    }

    /*
     * The URL that triggers log out to occur (default is "/logout").
     * If CSRF protection is enabled (default), then the request must
     * also be a POST. This means that by default POST "/logout" is
     * required to trigger a log out. If CSRF protection is disabled,
     * then any HTTP method is allowed. It is considered best practice
     * to use an HTTP POST on any action that changes state (i.e.
     * log out) to protect against CSRF attacks.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .and()
            .logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
            .httpBasic()
                .and()
            .csrf().disable();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
