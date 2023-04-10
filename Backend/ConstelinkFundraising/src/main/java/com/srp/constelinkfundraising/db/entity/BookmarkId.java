package com.srp.constelinkfundraising.db.entity;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class BookmarkId implements Serializable {
	private static final long serialVersionUID = -826361681759037356L;
	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "fundraising_id", nullable = false)
	private Long fundraisingId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		BookmarkId entity = (BookmarkId)o;
		return Objects.equals(this.fundraisingId, entity.fundraisingId) &&
			Objects.equals(this.memberId, entity.memberId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fundraisingId, memberId);
	}

}