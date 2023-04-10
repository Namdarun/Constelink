package com.srp.constelinknotice.dto;

import java.time.LocalDateTime;

import com.srp.constelinknotice.dto.enums.NoticeType;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeInfoDto {
	private Long id;
	private String noticeTitle;
	@Lob
	private String noticeContent;
	private LocalDateTime noticeRegDate;
	private NoticeType noticeType;
	private boolean noticeIsPinned;

}
