package br.com.pizzaria.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.pizzaria.domain.ErrorMessage;
import br.com.pizzaria.util.JsonToStringConverter;

@Service
public class ErrorMessageService {

	public String response(String message, String description, HttpStatus httpStatus) {
		ErrorMessage msg = new ErrorMessage(message, description, httpStatus);
		return JsonToStringConverter.convert(msg);
	}
}
