package com.srp.constelinkbeneficiary.db.entity;

import java.sql.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "beneficiary")
public class Beneficiary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "beneficiary_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "hospital_id", nullable = false)
	private com.srp.constelinkbeneficiary.db.entity.Hospital hospital;

	@Column(name = "beneficiary_name", length = 10)
	private String beneficiaryName;

	@Column(name = "beneficiary_birthday")
	private Date beneficiaryBirthday;

	@Column(name = "beneficiary_disease", length = 50)
	private String beneficiaryDisease;

	@Column(name = "beneficiary_photo", length = 100)
	private String beneficiaryPhoto;

	@Column(name = "beneficiary_amount_raised", nullable = false)
	private int beneficiaryAmountRaised;

	@Column(name = "beneficiary_amount_goal", nullable = false)
	private int beneficiaryAmountGoal;

	@Column(name = "beneficiary_status", nullable = false)
	private String beneficiaryStatus;

}