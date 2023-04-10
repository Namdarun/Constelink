package com.srp.authserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/auth/role")
public class AuthController {

	@GetMapping("/member")
	public ResponseEntity checkRoleMember(){
		log.info("member 인증 컨트롤러에 도착했습니다");
		return ResponseEntity.ok("인증 완료");
	}
	@GetMapping("/hospital")
	public ResponseEntity checkRoleHospital(){
		log.info("hospital 인증 컨트롤러에 도착했습니다");
		return ResponseEntity.ok("인증 완료");
	}
	@GetMapping("/admin")
	public ResponseEntity checkRoleAdmin(){
		log.info("admin 인증 컨트롤러에 도착했습니다");
		return ResponseEntity.ok("인증 완료");
	}

}
