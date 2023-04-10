package com.srp.constelinkfundraising.db.dto.response;

import com.srp.constelinkfundraising.db.entity.Fundraising;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookmarkResponse {
	private Fundraising fundraising;
}
