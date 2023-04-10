package com.srp.authserver.jwt;

import java.security.Key;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.srp.authserver.common.exception.CustomException;
import com.srp.authserver.common.exception.CustomExceptionType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

	private static final String AUTHORITIES_KEY = "role";
	@Value("${jwt.secret}")
	private String secret;
	private Key key;

	@Override
	public void afterPropertiesSet() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);

	}
	public String getRoleByToken(String token) throws SignatureException, ExpiredJwtException{  // Token 에 들어있는 role 얻기
		String accessToken = isBearerToken(token);

		Claims body = Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(accessToken)
				.getBody();
		return (String)body.get(AUTHORITIES_KEY);

	}

	private String isBearerToken(String token) {
		String[] tokenInfo = token.split(" ");

		if (!"Bearer".equals(tokenInfo[0])) {
			throw new CustomException(CustomExceptionType.NULL_TOKEN_EXCEPTION);
		} else {
			return tokenInfo[1];
		}
	}

}

