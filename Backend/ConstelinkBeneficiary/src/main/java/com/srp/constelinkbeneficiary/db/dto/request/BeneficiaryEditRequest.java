package com.srp.constelinkbeneficiary.db.dto.request;

import com.srp.constelinkbeneficiary.db.dto.enums.BeneficiaryStatus;

import lombok.Getter;

@Getter
public class BeneficiaryEditRequest {
	private BeneficiaryStatus beneficiaryStatus;
}
