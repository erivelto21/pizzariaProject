package br.com.pizzaria.util.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
	
	public static String convert(Object o) {
		ObjectMapper objectMapper = new ObjectMapper(); 
		
		try {
			return objectMapper.writeValueAsString(o);
		} catch(Exception e) {
			return "";
		}
	}
}
