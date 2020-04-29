package br.com.pizzaria.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={
		"br.com.pizzaria"})
@EntityScan("br.com.pizzaria.domain")
@EnableAuthorizationServer
@EnableResourceServer
public class Application {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/pizzaria");
		SpringApplication.run(Application.class, args);
	}
}
