package com.techsoft.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.Properties;

@SpringBootApplication
@EnableAsync
public class SpringSecurityApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityApplication.class, args);

	}



}
