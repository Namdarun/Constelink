package com.srp.constelinkmember.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.srp.constelinkmember.common.exception.CustomException;
import com.srp.constelinkmember.common.exception.CustomExceptionType;
import com.srp.constelinkmember.dto.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

	private static final String AUTHORITIES_KEY = "role";
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.access}")
	private long accessTokenValidTime;
	@Value("${jwt.refresh}")
	private long refreshTokenValidTime;

	private Key key;

	@Override
	public void afterPropertiesSet() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(Long memberId, Role role) {

		Date now = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(memberId))
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenValidTime * 1000))
			.claim(AUTHORITIES_KEY, role)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public String createRefreshToken() {
		Date now = new Date();

		String refreshToken = Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + refreshTokenValidTime * 1000))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return refreshToken;
	}

	public String resolveToken(String token) {  // Token에 들어있는 멤버아이디 얻기
		String accessToken = isBearerToken(token);
		return Jwts.parser()
			.setSigningKey(key)
			.parseClaimsJws(accessToken)
			.getBody()
			.getSubject();
	}

	public String getRoleByToken(String token) {  // Token 에 들어있는 role 얻기
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

	public long getExpiration(String token) {

		String accessToken = isBearerToken(token);

		Date expiration = Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody()
			.getExpiration();
		return expiration.getTime();
	}

}
