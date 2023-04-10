package com.srp.constelinkbeneficiary.db.dto.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryDiaryResponse {
	private Long diaryId;
	private Long beneficiaryId;
	private String beneficiaryName;
	private Long diaryRegisterDate;
	private String diaryPhoto;
	private String diaryTitle;
	private String diaryContent;
	private int diaryAmountSpent;

}
