package com.srp.authserver.filters;

import java.io.IOException;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import com.srp.authserver.dto.enums.Role;
import com.srp.authserver.jwt.TokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminFilter extends OncePerRequestFilter {
	private final TokenProvider tokenProvider;
	RedisTemplate<String, String> redisTemplate;

	public AdminFilter(TokenProvider tokenProvider, RedisTemplate<String, String> redisTemplate) {
		this.tokenProvider = tokenProvider;
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		log.info("Admin Filter 진입!");
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info(" " + accessToken);
		if (accessToken == null) {
			log.error("토큰이 비었다!");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 존재하지 않습니다");
			return;
		}
		String checkTrue = redisTemplate.opsForValue().get(accessToken);
		if (checkTrue != null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "허가되지 않은 토큰입니다");
		}

		String roleByToken = "";
		try {
			roleByToken = tokenProvider.getRoleByToken(accessToken);
		} catch (SignatureException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "변조된 토큰입니다");
			return;
		} catch (ExpiredJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 유효기간이 만료되었습니다");
			return;
		}

		log.info("role = " + roleByToken + " : " + Role.ADMIN);
		if (!Role.ADMIN.toString().equals(roleByToken)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한이 존재하지 않습니다");
			return;
		}

		filterChain.doFilter(request, response);

	}
}
