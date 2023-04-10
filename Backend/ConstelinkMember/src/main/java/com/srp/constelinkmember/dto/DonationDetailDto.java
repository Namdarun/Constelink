package com.srp.constelinkmember.dto;

import java.time.LocalDateTime;

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
public class DonationDetailDto {
	private String hospitalName;
	private String beneficiaryDisease;
	private int totalDonationPrice;
	private String beneficiaryName;
	private LocalDateTime lastDonationTime;
}
