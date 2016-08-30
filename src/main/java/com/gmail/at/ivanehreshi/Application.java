package com.gmail.at.ivanehreshi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Import({RootConfig.class, SecurityConfig.class, WebConfig.class})
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
