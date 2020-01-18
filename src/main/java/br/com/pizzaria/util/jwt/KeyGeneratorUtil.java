package br.com.pizzaria.util.jwt;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

public class KeyGeneratorUtil {
	
	public static Key generateKey() {
		String keyString = "cGl6emFyaWE="; //pizzaria em EncodeBase64;
		return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA256");
	}
}
