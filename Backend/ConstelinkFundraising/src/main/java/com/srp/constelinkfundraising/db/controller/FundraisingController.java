package com.srp.constelinkfundraising.db.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkfundraising.common.exception.CustomException;
import com.srp.constelinkfundraising.common.exception.CustomExceptionType;
import com.srp.constelinkfundraising.db.dto.enums.FundraisingByHopitalType;
import com.srp.constelinkfundraising.db.dto.enums.FundraisingSortType;
import com.srp.constelinkfundraising.db.dto.request.DonateRequest;
import com.srp.constelinkfundraising.db.dto.request.MakeFundraisingRequest;
import com.srp.constelinkfundraising.db.dto.response.FundraisingBeneficiaryResponse;
import com.srp.constelinkfundraising.db.dto.response.FundraisingResponse;
import com.srp.constelinkfundraising.db.dto.response.StatisticsResponse;
import com.srp.constelinkfundraising.db.entity.Fundraising;
import com.srp.constelinkfundraising.db.service.FundraisingService;
import com.srp.constelinkfundraising.jwt.JWTParser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "fundrasings", description = "기부 api")
@RestController
@RequestMapping("/fundraisings")
@RequiredArgsConstructor
@Slf4j
public class FundraisingController {

	private final FundraisingService fundraisingService;
	private final JWTParser jwtParser;
	@Operation(summary = "기부 리스트 열람 (memberId적으면 bookmark 반영되어 나옴), 병원이름 + 수혜자 이름 다 나옴", description =
		"page = 페이지, size = 한 페이지당 데이터 수, sortBy = 정렬타입, "
			)
	@GetMapping("/withbeneficiaryinfo")
	public ResponseEntity<Page<FundraisingResponse>> getFundraisings(
		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
		@RequestParam(name = "size", required = false, defaultValue = "5") int size,
		@RequestParam(name = "sortBy", required = false, defaultValue = "ALL") FundraisingSortType sortType,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long memberId;
		if(accessToken != null) {
			memberId = jwtParser.resolveToken(accessToken);
		} else {
			memberId = 0L;
		}
		Page<FundraisingResponse> fundraisingResponses
			= fundraisingService.getFundraisings(page - 1, size, sortType, memberId);
		return ResponseEntity.ok(fundraisingResponses);
	}

	@Operation(summary = "기부 리스트보기(병원 이름, 수혜자이름X)", description =
		"page = 페이지, size = 한 페이지당 데이터 수, sortBy = 정렬 타입 ☆ memberId ")
	@GetMapping("")
	public ResponseEntity<Page<FundraisingBeneficiaryResponse>> getFundraisingsBeneficiaries(
		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
		@RequestParam(name = "size", required = false, defaultValue = "5") int size,
		@RequestParam(name = "sortBy", required = false, defaultValue = "ALL") FundraisingSortType sortType,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long memberId;
		if(accessToken != null) {
			memberId = jwtParser.resolveToken(accessToken);
		} else {
			memberId = 0L;
		}
		Page<FundraisingBeneficiaryResponse> fundraisingBeneficiaryResponses
			= fundraisingService.getFundraisingsBeneficiaries(page - 1, size, sortType, memberId);
		return ResponseEntity.ok(fundraisingBeneficiaryResponses);
	}

	@PostMapping("/donate")
	public ResponseEntity<String> donateFundraising(
		@RequestBody DonateRequest donateRequest
	) {

		fundraisingService.donateFundraising(donateRequest);
		return ResponseEntity.ok("ok");
	}

	@Operation(summary = "기부 만들기", description = "beneficiaryId = 수혜자 Id, categoryId = 카테고리 Id,"
		+ " fundraisingAmountGoal = 기부 목표 금액, fundraisingEndTime = 기부 마감 시간,"
		+ " fundraisingTitle = 기부 제목, fundraisingStory = 기부 사연(내용), fundraisingThumbnail = 기부 썸네일, "
		+ "hospitalId는 header값있으면 그 값으로 확인, 헤더 없으면 hospitalId는 꼭 넣어서 사용")
	@PostMapping("/register")
	public ResponseEntity<Fundraising> makeFundraising(
		@RequestBody MakeFundraisingRequest makeFundraisingRequest,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(accessToken == null) {
			throw new CustomException(CustomExceptionType.HOSPITAL_NOT_FOUND);
		}
		Fundraising fundraisingResponse = fundraisingService.makeFundraising(makeFundraisingRequest);
		return ResponseEntity.ok(fundraisingResponse);
	}

	@Operation(summary = "기부 통계 가져오기", description = "기부 통계 가져오기")
	@GetMapping("/statistics")
	public ResponseEntity<StatisticsResponse> donateFundraising() {
		StatisticsResponse statisticsResponse = fundraisingService.getFundraisingStatistics();
		return ResponseEntity.ok(statisticsResponse);
	}

	@Operation(summary = "기부 데이터 카테고리 아이디로 찾기 나중에 Header 데이터 기반 북마크 체크 예정(categoryId가 0이면 전체 가져옴)", description =
		"page = 페이지, size = 한 페이지당 데이터 수, categoryId = 카테고리 아이디"
			)
	@GetMapping("/bycategory")
	public ResponseEntity<Page<FundraisingResponse>> getFundraisingByCategory(
		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
		@RequestParam(name = "size", required = false, defaultValue = "5") int size,
		@RequestParam(name = "categoryId", required = false, defaultValue = "0") Long categoryId,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long memberId;
		if(accessToken != null) {
			memberId = jwtParser.resolveToken(accessToken);
		} else {
			memberId = 0L;
		}
		Page<FundraisingResponse> fundraisingResponses = fundraisingService.fundraisingByCategory(categoryId,memberId, page-1, size);
		return ResponseEntity.ok(fundraisingResponses);
	}

	@Operation(summary = "병원 Id로 찾기", description =
		"page = 페이지, size = 한 페이지당 데이터 수, hospitalId = 병원 아이디, "
			)
	@GetMapping("/byhospital")
	public ResponseEntity<Page<FundraisingResponse>> getFundraisingByHospital(
		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
		@RequestParam(name = "size", required = false, defaultValue = "5") int size,
		@RequestParam(name = "sortBy", required = false, defaultValue = "NONE") FundraisingByHopitalType sortType,
		@RequestParam(name = "hospitalId", required = true) Long hospitalId,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long memberId;
		if(accessToken != null) {
			memberId = jwtParser.resolveToken(accessToken);
		} else {
			memberId = 0L;
		}
		if(hospitalId <1) {
			throw new CustomException(CustomExceptionType.HOSPITAL_NOT_FOUND);
		}
		Page<FundraisingResponse> fundraisingResponses = fundraisingService.getFundraisingsByHospital(page-1,size, sortType, hospitalId, memberId);
		return ResponseEntity.ok(fundraisingResponses);
	}


	@GetMapping("/{fundraisingid}")
	public ResponseEntity<FundraisingResponse> getFundraisingByHospital(
		@PathVariable(value = "fundraisingid", required = true) Long fundraisingId,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long memberId;
		if(accessToken != null) {
			memberId = jwtParser.resolveToken(accessToken);
		} else {
			memberId = 0L;
		}
		if(fundraisingId <1) {
			throw new CustomException(CustomExceptionType.FUNDRAISING_NOT_FOUND);
		}

		FundraisingResponse fundraisingResponses = fundraisingService.getFundraising(fundraisingId, memberId);
		return ResponseEntity.ok(fundraisingResponses);
	}

	@Operation(summary = "병원 Id로 찾기", description =
		"page = 페이지, size = 한 페이지당 데이터 수, hospitalId = 병원 아이디, "
	)
	@GetMapping("/byhospital/self")
	public ResponseEntity<Page<FundraisingResponse>> getFundraisingByHospitalSelf(
		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
		@RequestParam(name = "size", required = false, defaultValue = "5") int size,
		@RequestParam(name = "sortBy", required = false, defaultValue = "NONE") FundraisingByHopitalType sortType,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long hospitalId;
		if(accessToken == null) {
			throw new CustomException(CustomExceptionType.TOKEN_NOT_FOUND);
		} else {
			hospitalId = jwtParser.resolveToken(accessToken);
		}
		Long memberId;

		Page<FundraisingResponse> fundraisingResponses = fundraisingService.getFundraisingsByHospital(page-1,size, sortType, hospitalId, 0L);
		return ResponseEntity.ok(fundraisingResponses);
	}
}
