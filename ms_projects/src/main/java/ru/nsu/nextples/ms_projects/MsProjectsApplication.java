package ru.nsu.nextples.ms_projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsProjectsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProjectsApplication.class, args);
	}

}
