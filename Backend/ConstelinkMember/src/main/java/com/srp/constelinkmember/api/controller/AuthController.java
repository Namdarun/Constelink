package com.srp.constelinkmember.api.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkmember.api.service.AuthService;
import com.srp.constelinkmember.dto.LoginInfoDto;
import com.srp.constelinkmember.dto.request.LoginRequest;
import com.srp.constelinkmember.dto.response.LoginResponse;
import com.srp.constelinkmember.util.CookieUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증", description = "인증 관련 api 입니다.")
@Slf4j
public class AuthController {
	private final AuthService authService;

	@Operation(summary = "로그인 메서드", description = "로그인 메서드입니다.")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getKey() + loginRequest.isFlag());
		LoginInfoDto loginInfoDto = authService.login(loginRequest);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + loginInfoDto.getAccessToken());
		httpHeaders.add("refresh", loginInfoDto.getRefreshToken());

		LoginResponse loginResponse = LoginResponse.builder()
			.nickname(loginInfoDto.getNickname())
			.profile(loginInfoDto.getProfile())
			.role(loginInfoDto.getRole())
			.build();

		return ResponseEntity.ok().headers(httpHeaders).body(loginResponse);
	}

	@Operation(summary = "로그아웃 메서드", description = "로그아웃 메서드입니다.")
	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		String refreshToken = request.getHeader("refresh");
		authService.logout(accessToken, refreshToken);
		return ResponseEntity.ok("로그아웃 완료");
	}

	@Operation(summary = "토큰 재발급 메서드", description = "토큰 재발급 메서드입니다.")
	@PostMapping("/reissue")
	public ResponseEntity<String> reIssue(HttpServletRequest request) {

		String refreshToken = request.getHeader("refresh");
		String accessToken = authService.reGenerateToken(refreshToken);
		log.info("AccessToken 재 발급 완료!!" + accessToken);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		return ResponseEntity.ok().headers(httpHeaders).body("토큰 재발급 완료");
	}

}
