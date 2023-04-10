package com.srp.constelinkbeneficiary.db.dto.request;

import lombok.Getter;

@Getter
public class RecoveryDiaryRequest {
	Long beneficiaryId;
	String diaryPhoto;
	String diaryTitle;
	String diaryContent;
	int diaryAmountSpent;
}
