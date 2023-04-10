package com.srp.constelinkbeneficiary.db.dto.response;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeneficiaryInfoResponse {
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
	private String beneficiaryStatus;

}
