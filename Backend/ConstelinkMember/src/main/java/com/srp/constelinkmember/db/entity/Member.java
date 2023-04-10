package com.srp.constelinkmember.db.entity;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import com.srp.constelinkmember.dto.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "member")
public class Member {
	@Id
	@Column(name = "member_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Size(max = 50)
	@NotNull
	@Column(name = "username", nullable = false, length = 50)
	private String username;

	@Size(max = 50)
	@NotNull
	@Column(name = "email", nullable = false, length = 50)
	private String email;

	@Size(max = 300)
	@Column(name = "member_profile_img", length = 100)
	private String memberProfileImg;

	@NotNull
	@Column(name = "member_regdate", nullable = false)
	private LocalDateTime memberRegdate;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 10)
	private Role role;

	@Size(max = 100)
	@NotNull
	@Column(name = "social_id", nullable = false, length = 100)
	private String socialId;

	@NotNull
	@Column(name = "member_total_amount_raised", nullable = false)
	private int memberTotalAmountRaised;

	@NotNull
	@Column(name = "member_point", nullable = false)
	private int memberPoint;

	@NotNull
	@Builder.Default
	@Column(name = "member_inactive", nullable = false)
	private Boolean memberInactive = false;

	@Builder.Default
	@OneToMany
	private Set<Donation> donations = new LinkedHashSet<>();

	public void addAmountRaised(int amount) {
		this.memberTotalAmountRaised += amount;
	}

	public void addPoint(int price) {
		double point = Math.round(price * 0.005);
		this.memberPoint += point;
	}

}