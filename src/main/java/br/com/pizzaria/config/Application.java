package br.com.pizzaria.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={
		"br.com.pizzaria"})
@EntityScan("br.com.pizzaria.domain")
public class Application {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/pizzaria");
		SpringApplication.run(Application.class, args);
	}
}
