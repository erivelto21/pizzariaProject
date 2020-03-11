package br.com.pizzaria.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.pizzaria.service.ErrorMessageService;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class ExceptionFilter extends GenericFilterBean{

	@Autowired
	private ErrorMessageService service;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			response.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		} catch(JsonMappingException e) {
			response.getWriter().write(
					this.service.response("Json null", "Json estar vazio", HttpStatus.BAD_REQUEST));
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(400);
			
			return;
		}catch(ExpiredJwtException e) {
			response.getWriter().write(
					this.service.response("JWT Token inválido", "O token expirou", HttpStatus.BAD_REQUEST));
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(400);
			
			return;
		}catch(Exception e) {
			response.getWriter().write(
					this.service.response(
							e.getMessage(),
							e.toString(), 
							HttpStatus.INTERNAL_SERVER_ERROR));
			HttpServletResponse r = (HttpServletResponse) response;
			r.setStatus(500);
			
			return;
		}
	}

}
