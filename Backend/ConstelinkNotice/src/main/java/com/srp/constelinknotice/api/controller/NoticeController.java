package com.srp.constelinknotice.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinknotice.api.service.NoticeService;
import com.srp.constelinknotice.dto.NoticeInfoDto;
import com.srp.constelinknotice.dto.request.DeleteNoticeRequest;
import com.srp.constelinknotice.dto.request.ModifyNoticeRequest;
import com.srp.constelinknotice.dto.request.SaveNoticeRequest;
import com.srp.constelinknotice.dto.response.NoticeIdResponse;
import com.srp.constelinknotice.dto.response.NoticeListResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("notices")
@RequiredArgsConstructor
@Tag(name = "공지사항", description = "공지사항 관련 api 입니다.")
@Slf4j
public class NoticeController {

	private final NoticeService noticeService;

	@Operation(summary = "공지사항 저장", description = "공지사항 저장 메서드.")
	@PostMapping("/save")
	public ResponseEntity saveNotice(@RequestBody SaveNoticeRequest saveNoticeRequest) {
		NoticeIdResponse response = noticeService.saveNotice(saveNoticeRequest);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/list")
	public ResponseEntity listNotice(@RequestParam("page") int page) {

		NoticeListResponse noticeListResponse = noticeService.noticeList(page - 1);

		return ResponseEntity.ok(noticeListResponse);
	}

	@GetMapping("/detail")
	public ResponseEntity detailNotice(@RequestParam("id") Long id) {

		NoticeInfoDto noticeInfoDto = noticeService.noticeDetail(id);

		return ResponseEntity.ok(noticeInfoDto);
	}

	@PostMapping("/modify")
	public ResponseEntity modifyNotice(@RequestBody ModifyNoticeRequest modifyNoticeRequest) {
		noticeService.modifyNotice(modifyNoticeRequest);

		return ResponseEntity.ok("공지사항 수정 완료");
	}

	@PostMapping("/delete")
	public ResponseEntity deleteNotice(@RequestBody DeleteNoticeRequest deleteNoticeRequest) {
		noticeService.deleteNotice(deleteNoticeRequest);

		return ResponseEntity.ok("공지사항 삭제 완료");
	}

}
