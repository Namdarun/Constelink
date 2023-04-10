package com.srp.constelinkmember.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkmember.api.service.DonationService;
import com.srp.constelinkmember.dto.request.SaveDonationRequest;
import com.srp.constelinkmember.dto.response.BeneficiaryResponse;
import com.srp.constelinkmember.dto.response.DonationDetailsResponse;
import com.srp.constelinkmember.dto.response.StatsDataResponse;
import com.srp.constelinkmember.security.jwt.TokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("donations")
@Tag(name = "기부 기록", description = "기부 기록 관련 api 입니다.")
@Slf4j
public class DonationController {

	private final DonationService donationService;
	private final TokenProvider tokenProvider;

	@Operation(summary = "기부저장", description = "기부내역 저장 메서드.")
	@PostMapping("/save")
	public ResponseEntity saveDonation(@RequestBody SaveDonationRequest saveRequest,
		HttpServletRequest request) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		String id = tokenProvider.resolveToken(accessToken);
		Long memberId = Long.valueOf(id);

		donationService.saveDonation(saveRequest, memberId);

		return ResponseEntity.ok("정상적으로 저장 되었습니다");
	}

	@Operation(summary = "기부내역 조회", description = "기부내역 조회 메서드.")
	@GetMapping("/list")
	public ResponseEntity listDonation(@RequestParam("page") int page, HttpServletRequest request) {
		 String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		 String id = tokenProvider.resolveToken(accessToken);
		 Long memberId = Long.valueOf(id);
		DonationDetailsResponse donationDetailsResponse = donationService.listDonation(memberId, page - 1);
		return ResponseEntity.ok(donationDetailsResponse);
	}

	@Operation(summary = "기부통계 자료 반환", description = "기부통계 자료 반환 메서드.")
	@GetMapping("/data")
	public ResponseEntity responseData() {
		StatsDataResponse response = donationService.getStatsData();

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "수혜자 번호 반환", description = "사용자가 기부한 회복일지를 보기위한 데이터 반환")
	@GetMapping("/beneficiarys")
	public ResponseEntity getBeneficiaryIds(@RequestParam("id") Long memberId) {
		BeneficiaryResponse beneficiaryResponse = donationService.getBeneficiaryIds(memberId);

		return ResponseEntity.ok(beneficiaryResponse);
	}

}
