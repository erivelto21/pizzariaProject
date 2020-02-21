package br.com.pizzaria.filter.failure.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import br.com.pizzaria.service.ErrorMessageService;

public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private ErrorMessageService service;
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(401);

		response.getWriter().write(this.service.response("E-mail ou senha Inválidos", "E-mail ou senha Inválidos", HttpStatus.UNAUTHORIZED));
	}

}
