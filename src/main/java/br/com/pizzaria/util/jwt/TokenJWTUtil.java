package br.com.pizzaria.util.jwt;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenJWTUtil {
	
	private static final long EXPIRATION_TIME = 86000000;// almost one day
	private static final String SECRET = "cGl6emFyaWE";//pizzaria into EncodeBase64;
	
	public static String generateTokenJWT(String username, List<String> roles) {
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuer("pizzaria")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("roles", roles)
                .compact();
	}
	
	public static Authentication getAuthentication(String token) {
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;
	}
}
