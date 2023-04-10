package com.srp.constelinkmember.dto.request;

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
public class SaveDonationRequest {

	private Long fundraisingId;
	private int donationPrice;
	private String donationTransactionHash;
	private String hospitalName;
	private Long beneficiary_id;
	private String beneficiaryName;
	private String beneficiaryDisease;
	private String fundraisingTitle;
	private String fundraisingThumbnail;

}
