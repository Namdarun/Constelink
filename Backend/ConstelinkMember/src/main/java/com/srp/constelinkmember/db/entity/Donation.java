package com.srp.constelinkmember.db.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "donation")
public class Donation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "donation_id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@NotNull
	@Column(name = "fundraising_id", nullable = false)
	private Long fundraisingId;

	@NotNull
	@Column(name = "donation_price", nullable = false)
	private int donationPrice;

	@NotNull
	@Column(name = "donation_time", nullable = false)
	private LocalDateTime donationTime;

	@Size(max = 255)
	@Column(name = "donation_transaction_hash")
	private String donationTransactionHash;

	@Size(max = 20)
	@NotNull
	@Column(name = "hospital_name", nullable = false, length = 20)
	private String hospitalName;

	@Size(max = 50)
	@NotNull
	@Column(name = "beneficiary_disease", nullable = false, length = 50)
	private String beneficiaryDisease;

	@Size(max = 50)
	@NotNull
	@Column(name = "fundraising_title", nullable = false, length = 50)
	private String fundraisingTitle;

	@Size(max = 300)
	@Column(name = "fundraising_thumbnail", length = 100)
	private String fundraisingThumbnail;

	@Size(max = 10)
	@Column(name = "beneficiary_name", length = 10)
	private String beneficiaryName;

	@NotNull
	@Column(name = "beneficiary_id", nullable = false)
	private Long beneficiaryId;

}