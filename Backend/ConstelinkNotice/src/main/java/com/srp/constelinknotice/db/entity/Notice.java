package com.srp.constelinknotice.db.entity;

import java.time.LocalDateTime;

import com.srp.constelinknotice.dto.enums.NoticeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id", nullable = false)
	private Long id;

	@Column(name = "notice_title", nullable = false, length = 100)
	private String noticeTitle;

	@Lob
	@Column(name = "notice_content", nullable = false)
	private String noticeContent;

	@Column(name = "notice_regdate", nullable = false)
	private LocalDateTime noticeRegdate;

	@Enumerated(EnumType.STRING)
	@Column(name = "notice_type", nullable = false, length = 20)
	private NoticeType noticeType;

	@Column(name = "notice_pinned", nullable = false)
	private boolean noticePinned;

}