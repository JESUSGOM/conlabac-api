package com.aliro5.conlabac_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ConlabacApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConlabacApiApplication.class, args);
	}

}
