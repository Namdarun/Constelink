package com.srp.constelinkmember.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkmember.api.service.KakaoPayService;
import com.srp.constelinkmember.dto.request.KakaoPayRequest;
import com.srp.constelinkmember.dto.response.KakaoApproveResponse;
import com.srp.constelinkmember.dto.response.KakaoReadyResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name = "카카오페이", description = "카카오페이 관련 API 입니다")
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;

	@Operation(summary = "카카오페이 결제 준비", description = "카카오페이 결제 준비 메서드.")
	@PostMapping("/ready")
	public KakaoReadyResponse readyToKakaoPay(@RequestBody KakaoPayRequest kakaoPayRequest) {
		return kakaoPayService.kakaoPayReady(kakaoPayRequest);
	}

	@Operation(summary = "카카오페이 결제 성공시 정보요청", description = "카카오페이 결제 성공시 정보요청 메서드.")
	@GetMapping("/success")
	public ResponseEntity successKakaoPay(@RequestParam("pg_token") String pgToken) {
		KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken);
		return ResponseEntity.ok(kakaoApprove);
	}

}
