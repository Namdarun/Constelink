package com.srp.constelinkbeneficiary.db.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BeneficiaryByDiaryDateResponse {
	private Long beneficiaryId;
	private String beneficiaryName;
	private Long beneficiaryBirthday;
	private String beneficiaryDisease;
	private String beneficiaryPhoto;
	private int beneficiaryAmountRaised;
	private int beneficiaryAmountGoal;
	private Long hospitalId;
	private String hospitalName;
	private String hospitalLink;
	private Long diaryFinishedDate;
}
