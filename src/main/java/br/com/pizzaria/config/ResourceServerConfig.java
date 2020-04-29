package br.com.pizzaria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/oauth/token").permitAll()
				.antMatchers("/flavor").permitAll()
				.antMatchers("/pizza").permitAll()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.anyRequest().authenticated();
	}
}
