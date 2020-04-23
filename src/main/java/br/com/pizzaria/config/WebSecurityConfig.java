package br.com.pizzaria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.pizzaria.filter.ExceptionFilter;
import br.com.pizzaria.filter.JWTAuthenticationFilter;
import br.com.pizzaria.filter.JWTLoginFilter;
import br.com.pizzaria.filter.failure.handler.LoginAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private ExceptionFilter exceptionFilter;

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/flavor").permitAll()
				.antMatchers("/pizza").permitAll()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.anyRequest().authenticated().and()
				.addFilterBefore(exceptionFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.exposedHeaders("Authorization")
				.allowedHeaders("*")
				.allowedMethods("*")
				.allowedOrigins("*");
			}
		};
	}
	
	@Bean
	public JWTLoginFilter jwtLoginFilter() throws Exception {
		return new JWTLoginFilter(authenticationManager(), loginAuthenticationFailureHandler());
	}

	@Bean
	public LoginAuthenticationFailureHandler loginAuthenticationFailureHandler() {
		return new LoginAuthenticationFailureHandler();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
