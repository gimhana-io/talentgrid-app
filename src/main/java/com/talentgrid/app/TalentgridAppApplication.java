package com.talentgrid.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class TalentgridAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentgridAppApplication.class, args);
	}

}
