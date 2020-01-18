package br.com.pizzaria.util.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenJWTUtil {
	
	static final long EXPIRATION_TIME = 86000000;// quase 1 dia
	
	public static String gerarTokenJWT(String username, List<String> roles) {
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, KeyGeneratorUtil.generateKey())
				.setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuer("pizzaria")//trocar dps
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("roles", roles)
                .compact();
	}
	
	public static boolean tokenValido(String token, Key key) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
