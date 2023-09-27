package org.spring.securityOauth2_ex1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecurityOauth2Ex1Application {

	public static void main(String[] args) {
		SpringApplication.run(SecurityOauth2Ex1Application.class, args);
	}

}
