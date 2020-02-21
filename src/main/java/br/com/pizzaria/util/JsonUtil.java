package br.com.pizzaria.util;

import java.io.IOException;

import javax.ws.rs.InternalServerErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {
	
	public static String objectToJson(Object o) {
		ObjectMapper objectMapper = new ObjectMapper(); 

		try {
			return objectMapper.writeValueAsString(o);
		} catch(Exception e) {
			throw new InternalServerErrorException("Um error aconteceu durante a conversão para JSON");
		}
	}
	
	public static String getJsonValue(String json, String name) {
		String value = "";
		
		try {
			ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);

			if (node.has(name))
				value = removeDoubleQuotes(node.get(name).toString());
		} catch (IOException e) {
			throw new InternalServerErrorException("Um error aconteceu durante a obtenção do valor do json");
		}
		
		return value;
	}
	
	
	private static String removeDoubleQuotes(String string) {
		return string.replace("\"", "");
	}
}
