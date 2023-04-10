package com.srp.constelinkfundraising.db.dto.response;

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
public class StatsDataResponse {
	private int allDonation;
	private int allMember;
	private int allHospital;

}
