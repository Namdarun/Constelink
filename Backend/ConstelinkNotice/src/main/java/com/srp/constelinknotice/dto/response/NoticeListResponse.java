package com.srp.constelinknotice.dto.response;

import java.util.List;

import com.srp.constelinknotice.dto.NoticeInfoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeListResponse {

	List<NoticeInfoDto> noticeList;
	private int totalPages;
	private long totalElements;
}
