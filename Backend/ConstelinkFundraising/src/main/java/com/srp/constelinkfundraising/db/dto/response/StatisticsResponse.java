package com.srp.constelinkfundraising.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponse {
	private Long totalFundraisings;
	private Long totalFundraisingsFinished;
	private Long totalAmountedCash;
	private int allDonation;
	private int allMember;
	private int allHospital;
}
