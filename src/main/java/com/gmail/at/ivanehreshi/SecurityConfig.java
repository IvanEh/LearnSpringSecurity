package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
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
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
            .httpBasic();
    }
}
