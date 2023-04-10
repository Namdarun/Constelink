package com.srp.constelinknotice.dto.request;

import com.srp.constelinknotice.dto.enums.NoticeType;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyNoticeRequest {

	private Long id;
	private String noticeTitle;
	@Lob
	private String noticeContent;
	private NoticeType noticeType;
	private boolean noticeIsPinned;

}
