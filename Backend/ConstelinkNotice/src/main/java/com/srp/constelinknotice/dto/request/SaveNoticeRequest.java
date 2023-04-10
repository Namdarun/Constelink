package com.srp.constelinknotice.dto.request;

import com.srp.constelinknotice.dto.enums.NoticeType;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveNoticeRequest {

	private String noticeTitle;
	@Lob
	private String noticeContent;
	private NoticeType noticeType;
	private boolean noticeIsPinned;
}
