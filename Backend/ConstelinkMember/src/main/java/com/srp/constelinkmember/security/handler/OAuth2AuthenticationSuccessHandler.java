package com.srp.constelinkmember.security.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.srp.constelinkmember.security.principal.MemberPrincipalDetail;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final RedisTemplate<String, Object> redisTemplate;

	@Value("${login.redirect-url}")
	private String redirect;

	/**
	 * PrincipalOauth2UserService 에서 return 한 OAuthUser 타입의 객체가 ContextHolder에 올라가있기 때문에 Authentication 객체에서 꺼낼 수 있음
	 *
	 * @param request        the request which caused the successful authentication
	 * @param response       the response
	 * @param authentication the <tt>Authentication</tt> object which was created during
	 *                       the authentication process.
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		MemberPrincipalDetail principalDetail = (MemberPrincipalDetail)authentication.getPrincipal();
		Long memberId = principalDetail.getMemberId();

		UUID randomId = UUID.randomUUID();

		String redisKey = "ID:" + randomId;
		redisTemplate.opsForValue().set(redisKey, memberId, 10, TimeUnit.SECONDS);

		response.sendRedirect(UriComponentsBuilder.fromUriString(redirect)
			.queryParam("connect-id", redisKey)
			.queryParam("flag", principalDetail.isInactive())
			.build()
			.encode(StandardCharsets.UTF_8)
			.toUriString());
		log.info("Success Handler 입니다 회원의 PK 값은 ==  " + principalDetail.getMemberId());
		log.info("OAuth2 Login 성공");
	}
}
