package com.srp.constelinkfundraising.db.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkfundraising.common.exception.CustomException;
import com.srp.constelinkfundraising.common.exception.CustomExceptionType;
import com.srp.constelinkfundraising.db.dto.request.BookmarkFundraisingRequest;
import com.srp.constelinkfundraising.db.dto.response.FundraisingResponse;
import com.srp.constelinkfundraising.db.service.BookmarkService;
import com.srp.constelinkfundraising.jwt.JWTParser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "bookmark", description = "기부 북마크 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

	private final BookmarkService bookmarkService;
	private final JWTParser jwtParser;
	// Bookmark 등록 or 해체
	@Operation(summary = "회원의 북마크 추가/삭제", description = "memberId = 회원 Id, fundraisingId = 모금 Id")
	@PostMapping("")
	public ResponseEntity<Boolean> bookmarkFundraising(
		@RequestBody BookmarkFundraisingRequest bookmarkFundraisingRequest,
		HttpServletRequest request
	) {
		Long memberId;
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(accessToken == null) {
			throw new CustomException(CustomExceptionType.TOKEN_NOT_FOUND);
		} else {
			memberId = jwtParser.resolveToken(accessToken);
		}
		return ResponseEntity.ok(bookmarkService.bookmarkFundraising(bookmarkFundraisingRequest,memberId));
	}

	@Operation(summary = "회원의 북마크한 목록 조회", description = "page = 페이지, size = 페이지당 데이터 수, memberId = 회원 Id")
	@GetMapping("")
	public ResponseEntity<Page<FundraisingResponse>> getBookmarks(
		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
		@RequestParam(name = "size", required = false, defaultValue = "5") int size,
		HttpServletRequest request
	) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		Long memberId = jwtParser.resolveToken(accessToken);
		if(accessToken == null) {
			throw new CustomException(CustomExceptionType.TOKEN_NOT_FOUND);
		}
		return ResponseEntity.ok(bookmarkService.getBookmarks(memberId, page - 1, size));
	}
}
