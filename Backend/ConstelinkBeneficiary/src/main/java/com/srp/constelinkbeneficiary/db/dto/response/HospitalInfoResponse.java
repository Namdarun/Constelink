package com.srp.constelinkbeneficiary.db.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalInfoResponse {
	private Long hospitalId;
	private String hospitalName;
	private int hospitalTotalAmountRaised;
	private int hospitalTotalBeneficiary;
	private String hospitalWalletAddress;
	private String hospitalLink;
}
