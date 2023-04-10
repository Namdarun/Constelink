package com.srp.constelinkbeneficiary.db.dto.response;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryDiaryBeneficiaryResponse {
	BeneficiaryInfoResponse beneficiaryInfo;
	Page<RecoveryDiaryResponse> beneficiaryDiaries;
}
