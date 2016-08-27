package com.gmail.at.ivanehreshi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Import({RootConfig.class, SecurityConfig.class, WebConfig.class})
@RestController
public class Application {

	@Bean
    public Integer intBean() {
        return 1;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
