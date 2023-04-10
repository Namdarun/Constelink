package com.srp.constelinkfundraising.db.entity;

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
@Table(name = "fundraising")
public class Fundraising {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fundraising_id", nullable = false)
	private Long id;

	@Column(name = "beneficiary_id", nullable = false)
	private Long beneficiaryId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "fundraising_amount_raised", nullable = false)
	private int fundraisingAmountRaised;

	@Column(name = "fundraising_amount_goal", nullable = false)
	private int fundraisingAmountGoal;

	@Column(name = "fundraising_start_time", nullable = false)
	private LocalDateTime fundraisingStartTime;

	@Column(name = "fundraising_end_time", nullable = false)
	private LocalDateTime fundraisingEndTime;

	@Column(name = "fundraising_title", length = 50)
	private String fundraisingTitle;
	@Lob
	@Column(name = "fundraising_story")
	private String fundraisingStory;

	@Column(name = "fundraising_thumbnail", length = 100)
	private String fundraisingThumbnail;

	@Column(name = "fundraising_people", nullable = false)
	private int fundraisingPeople;

	@Column(name = "fundraising_is_done")
	private boolean fundraisingIsDone;

	@Column(name = "hospital_name", length = 20)
	private String hospitalName;

	@Column(name = "hospital_id", nullable = false)
	private Long hospitalId;

	@Column(name = "fundraising_will_use", length = 200)
	private String fundraisingWillUse;
}