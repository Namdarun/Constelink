package com.srp.constelinknotice.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srp.constelinknotice.common.exception.CustomException;
import com.srp.constelinknotice.common.exception.CustomExceptionType;
import com.srp.constelinknotice.db.entity.Notice;
import com.srp.constelinknotice.db.repository.NoticeRepository;
import com.srp.constelinknotice.dto.NoticeInfoDto;
import com.srp.constelinknotice.dto.request.DeleteNoticeRequest;
import com.srp.constelinknotice.dto.request.ModifyNoticeRequest;
import com.srp.constelinknotice.dto.request.SaveNoticeRequest;
import com.srp.constelinknotice.dto.response.NoticeIdResponse;
import com.srp.constelinknotice.dto.response.NoticeListResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeRepository noticeRepository;

	@Transactional
	public NoticeIdResponse saveNotice(SaveNoticeRequest saveNoticeRequest) {
		Notice notice = Notice.builder()
			.noticeTitle(saveNoticeRequest.getNoticeTitle())
			.noticeContent(saveNoticeRequest.getNoticeContent())
			.noticeRegdate(LocalDateTime.now())
			.noticeType(saveNoticeRequest.getNoticeType())
			.noticePinned(saveNoticeRequest.isNoticeIsPinned())
			.build();

		Notice saveNotice = noticeRepository.save(notice);
		NoticeIdResponse response = NoticeIdResponse.builder()
			.id(saveNotice.getId())
			.build();

		return response;
	}

	public NoticeListResponse noticeList(int page) {
		PageRequest pageRequest = PageRequest.of(page, 8,
			Sort.by(Sort.Direction.DESC, "noticeRegdate"));

		Page<Notice> notices = noticeRepository.findAll(pageRequest);

		List<NoticeInfoDto> noticeInfoList = notices.getContent().stream().map(notice ->
			new NoticeInfoDto().builder()
				.id(notice.getId())
				.noticeTitle(notice.getNoticeTitle())
				.noticeContent(notice.getNoticeContent())
				.noticeRegDate(notice.getNoticeRegdate())
				.noticeType(notice.getNoticeType())
				.noticeIsPinned(notice.isNoticePinned())
				.build()).collect(Collectors.toList());

		NoticeListResponse noticeListResponse = NoticeListResponse.builder()
			.noticeList(noticeInfoList)
			.totalPages(notices.getTotalPages())
			.totalElements(notices.getTotalElements())
			.build();

		return noticeListResponse;
	}

	@Transactional
	public void modifyNotice(ModifyNoticeRequest modifyNoticeRequest) {
		Optional<Notice> findNotice = noticeRepository.findById(modifyNoticeRequest.getId());

		if (findNotice.isPresent()) {
			Notice notice = findNotice.get();
			notice.setNoticeTitle(modifyNoticeRequest.getNoticeTitle());
			notice.setNoticeContent(modifyNoticeRequest.getNoticeContent());
			notice.setNoticeType(modifyNoticeRequest.getNoticeType());
			notice.setNoticePinned(modifyNoticeRequest.isNoticeIsPinned());

		} else {
			throw new CustomException(CustomExceptionType.NOTICE_NOT_FOUND);
		}

	}

	@Transactional
	public void deleteNotice(DeleteNoticeRequest deleteNoticeRequest) {
		noticeRepository.deleteById(deleteNoticeRequest.getId());
	}

	public NoticeInfoDto noticeDetail(Long id) {
		Optional<Notice> findNotice = noticeRepository.findById(id);
		if (!findNotice.isPresent()) {
			throw new CustomException(CustomExceptionType.NOTICE_NOT_FOUND);
		}
		Notice notice = findNotice.get();
		NoticeInfoDto noticeInfoDto = NoticeInfoDto.builder()
			.id(notice.getId())
			.noticeTitle(notice.getNoticeTitle())
			.noticeContent(notice.getNoticeContent())
			.noticeRegDate(notice.getNoticeRegdate())
			.noticeType(notice.getNoticeType())
			.noticeIsPinned(notice.isNoticePinned())
			.build();

		return noticeInfoDto;
	}
}
