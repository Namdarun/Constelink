package com.srp.constelinkbeneficiary.db.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkbeneficiary.common.exception.CustomException;
import com.srp.constelinkbeneficiary.common.exception.CustomExceptionType;
import com.srp.constelinkbeneficiary.db.dto.enums.RecoveryDiaryMemberDonatedType;
import com.srp.constelinkbeneficiary.db.dto.enums.RecoveryDiarySortType;
import com.srp.constelinkbeneficiary.db.dto.request.RecoveryDiaryRequest;
import com.srp.constelinkbeneficiary.db.dto.response.BeneficiaryInfoResponse;
import com.srp.constelinkbeneficiary.db.dto.response.RecoveryDiaryBeneficiaryResponse;
import com.srp.constelinkbeneficiary.db.dto.response.RecoveryDiaryResponse;
import com.srp.constelinkbeneficiary.db.service.RecoveryDiaryService;
import com.srp.constelinkbeneficiary.jwt.JWTParser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "회복일지 API", description = "회복일지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recoverydiaries")
public class RecoveryDiaryController {
	private final RecoveryDiaryService recoveryDiaryService;
	private final JWTParser jwtParser;

	@Operation(summary = "모든 회복일지 목록 조회", description = "page = 페이지, "
		+ "size = 한 페이지 자료 개수, "
		+ "sortBy = 정렬 타입")
	@GetMapping("")
	public ResponseEntity<Page<RecoveryDiaryResponse>> getRecoveryDiaries(
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@RequestParam(value = "size", required = false, defaultValue = "5") int size,
		@RequestParam(value = "sortBy", required = false, defaultValue = "DATE_DESC") RecoveryDiarySortType sortType) {
		Page<RecoveryDiaryResponse> recoveryDiaries = recoveryDiaryService.getRecoveryDiaryList(page - 1, size, sortType);
		return ResponseEntity.ok(recoveryDiaries);
	}

	@Operation(summary = "해당 수혜자 회복일지 목록 조회", description = "beneficiaryId = 수혜자 ID, "
		+ "page = 페이지, "
		+ "size = 한페이지 자료수, "
		+ "sortBy = 정렬 타입")
	@GetMapping("/{beneficiaryId}")
	public <T> ResponseEntity<RecoveryDiaryBeneficiaryResponse> getRecoveryDiaries(
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@RequestParam(value = "size", required = false, defaultValue = "5") int size,
		@RequestParam(value = "sortBy", required = false, defaultValue = "DATE_DESC") RecoveryDiarySortType sortType,
		@PathVariable(value = "beneficiaryId") Long beneficiaryId) {
		RecoveryDiaryBeneficiaryResponse recoveryDiaries = recoveryDiaryService.getRecoveryDiaryBeneficiaryList(page - 1, size, beneficiaryId, sortType);

		return ResponseEntity.ok(recoveryDiaries);
	}

	@Operation(summary = "회복일지 등록", description = ""
		+ "beneficiaryId = 수혜자 아이디, "
		+ "diaryTitle = 회복일지 제목, "
		+ "diaryContent = 회복일지 내용, "
		+ "diaryPhoto = 회복일지 사진, "
		+ "diaryAmountSpent = 사용한 기부금 "
		)
	@PostMapping("/register")
	public ResponseEntity<RecoveryDiaryResponse> addRecoveryDiary(
		@RequestBody RecoveryDiaryRequest recoveryDiaryRequest,
		HttpServletRequest request
	) {
		// Long hospitalId;
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(accessToken == null) {
			throw new CustomException(CustomExceptionType.AUTHORIZATION_ERROR);
		} else {
			// hospitalId = jwtParser.resolveToken(accessToken);
		}

		return ResponseEntity.ok(recoveryDiaryService.addRecoveryDiary(recoveryDiaryRequest));
	}

	@Operation(summary = "사용자가 기부한 수혜자들의 회복일지 목록 조회", description =
		"page = 페이지, "
		+ "size = 한페이지 자료수, "
		+ "sortBy = 정렬 타입")
	@GetMapping("/donated")
	public <T> ResponseEntity<Page<RecoveryDiaryResponse>> getMyDonatedRecoveryDiaries(
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@RequestParam(value = "size", required = false, defaultValue = "5") int size,
		@RequestParam(value = "sortBy", required = false, defaultValue = "DATE_DESC") RecoveryDiaryMemberDonatedType sortType,
		HttpServletRequest request) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long memberId;
		if(accessToken == null) {
			throw new CustomException(CustomExceptionType.TOKEN_NOT_FOUND);

		} else {
			memberId = jwtParser.resolveToken(accessToken);
		}

		Page<RecoveryDiaryResponse> recoveryDiaries = recoveryDiaryService.getRecoveryDiaryDonatedByMember(page - 1, size, memberId, sortType);

		return ResponseEntity.ok(recoveryDiaries);
	}

}
