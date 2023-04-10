package com.srp.constelinkbeneficiary.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hospital")
public class Hospital {
	@Id
	@Column(name = "hospital_id", nullable = false)
	private Long id;

	@Column(name = "hospital_name", length = 10)
	private String hospitalName;

	@Column(name = "hospital_total_amount_raised", nullable = false)
	private int hospitalTotalAmountRaised;

	@Column(name = "hospital_total_beneficiary", nullable = false)
	private int hospitalTotalBeneficiary;

	@Column(name = "hospital_wallet_address", length = 100)
	private String hospitalWalletAddress;

	@Column(name = "hospital_link", length = 100)
	private String hospitalLink;

}