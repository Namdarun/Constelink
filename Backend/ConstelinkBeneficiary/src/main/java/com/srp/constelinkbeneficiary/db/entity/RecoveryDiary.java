package com.srp.constelinkbeneficiary.db.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name = "recovery_diary")
public class RecoveryDiary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recovery_diary_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "beneficiary_id", nullable = false)
	private com.srp.constelinkbeneficiary.db.entity.Beneficiary beneficiary;

	@Column(name = "recovery_diary_regdate", nullable = false)
	private LocalDateTime recoveryDiaryRegdate;

	@Column(name = "recovery_diary_photo", length = 100)
	private String recoveryDiaryPhoto;

	@Column(name = "recovery_diary_title")
	private String recoveryDiaryTitle;
	@Lob
	@Column(name = "recovery_diary_content")
	private String recoveryDiaryContent;

	@Column(name = "recovery_diary_amount_spent", nullable = false)
	private int recoveryDiaryAmountSpent;

}