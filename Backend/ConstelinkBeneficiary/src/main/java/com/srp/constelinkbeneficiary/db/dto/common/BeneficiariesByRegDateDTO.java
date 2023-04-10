package com.srp.constelinkbeneficiary.db.dto.common;

import java.util.Date;

import lombok.Getter;

@Getter
public class BeneficiariesByRegDateDTO {
	String beneficiaryDisease;
	Integer beneficiaryAmountGoal;
	Integer beneficiaryAmountRaised;
	String beneficiaryName;
	String beneficiaryPhoto;
	Date beneficiaryBirthday;
	Long hospitalId;
	String hospitalName;
	String hospitalLink;
	Long beneficiaryId;
}
