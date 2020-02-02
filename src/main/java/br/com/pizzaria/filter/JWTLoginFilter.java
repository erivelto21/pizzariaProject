package br.com.pizzaria.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.service.SystemUserService;
import br.com.pizzaria.util.jwt.JsonConverter;
import br.com.pizzaria.util.jwt.TokenJWTUtil;

@Component
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter  {
	
	@Autowired
	private SystemUserService service;
	
	@Autowired
	public JWTLoginFilter(AuthenticationManager authManager) {
		super(new AntPathRequestMatcher("/login", "POST"));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		SystemUser user = new ObjectMapper().readValue(request.getInputStream(), SystemUser.class);
		
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
				user.getPassword(), Collections.emptyList()));
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {
		
		response.addHeader("Authorization", "Bearer" + TokenJWTUtil.gerarTokenJWT(auth.getName(), Collections.emptyList()));
		response.setContentType("application/json");
		String userJson = JsonConverter.convert(this.service.getSystemUser(auth.getName()));
		response.getWriter().write(userJson);
	}
}