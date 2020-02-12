package br.com.pizzaria.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToStringConverter {
	
	public static String convert(Object o) {
		ObjectMapper objectMapper = new ObjectMapper(); 
		
		try {
			return objectMapper.writeValueAsString(o);
		} catch(Exception e) {
			return "";
		}
	}
}
