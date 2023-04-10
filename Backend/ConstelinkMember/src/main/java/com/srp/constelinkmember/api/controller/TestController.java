package com.srp.constelinkmember.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkmember.dto.enums.Role;
import com.srp.constelinkmember.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

	private final TokenProvider tokenProvider;

	@GetMapping("/test")
	public String returnToken(@RequestParam("role") int role) {
		if (role == 0) {
			return tokenProvider.createAccessToken(1L, Role.MEMBER);
		} else if (role == 1) {
			return tokenProvider.createAccessToken(1L, Role.HOSPITAL);
		} else if (role == 2) {
			return tokenProvider.createAccessToken(1L, Role.ADMIN);
		}

		return null;
	}
}
