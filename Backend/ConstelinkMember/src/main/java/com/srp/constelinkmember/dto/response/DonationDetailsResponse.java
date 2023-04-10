package com.srp.constelinkmember.dto.response;

import java.util.List;

import com.srp.constelinkmember.dto.DonationDetailDto;

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
public class DonationDetailsResponse {
	//private List<DonationDetailDto> donations;

	private List<DonationDetailDto> donations;
	private int totalPages;
	private long totalElements;
}
