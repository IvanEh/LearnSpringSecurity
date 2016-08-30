package com.gmail.at.ivanehreshi;

import com.gmail.at.ivanehreshi.services.UserServiceImpl2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import javax.servlet.Filter;
import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        return new UserServiceImpl2(jdbcTemplate);
    }

    @Autowired
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder,
                                AuthenticationProvider authProvider) throws Exception {
        /**
         * Note that spring assumes a default db scheme with `users` and `authorities` tables.
         * We are using our own scheme so insertion(withUser()) won't work
         *
         * Manually create the scheme and add a user for testing
         */
        authBuilder.authenticationProvider(authProvider);
    }

    @Autowired
    @Bean
    public Filter digestFilter(UserDetailsService userDetailsService) {
        DigestAuthenticationFilter filter =
                new DigestAuthenticationFilter();
        filter.setUserDetailsService(userDetailsService);
        filter.setAuthenticationEntryPoint(digestEntryPoint());
        return filter;
    }

    @Bean
    public DigestAuthenticationEntryPoint digestEntryPoint() {
        DigestAuthenticationEntryPoint entryPoint =
                new DigestAuthenticationEntryPoint();
        entryPoint.setRealmName("Basic Realm");
        entryPoint.setKey("acegi");
        return entryPoint;
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
            .logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(digestEntryPoint())
                .and()
            .httpBasic()
                .and()
            .addFilterAfter(digestFilter(userDetailsService()),BasicAuthenticationFilter.class)
            .csrf().disable();


    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
