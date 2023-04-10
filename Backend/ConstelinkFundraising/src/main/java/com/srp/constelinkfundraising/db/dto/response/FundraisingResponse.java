package com.srp.constelinkfundraising.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundraisingResponse {

	private Long fundraisingId;
	private Long beneficiaryId;
	private String categoryName;
	private int fundraisingAmountRaised;
	private int fundraisingAmountGoal;
	private Long fundraisingStartTime;
	private Long fundraisingEndTime;
	private String fundraisingTitle;
	private String fundraisingStory;
	private String fundraisingThumbnail;
	private int fundraisingPeople;
	private boolean fundraisingIsDone;
	private Boolean fundraisingBookmarked;
	private String hospitalName;
	private String beneficiaryName;
	private String beneficiaryDisease;
	private String beneficiaryStatus;
	private Long beneficiaryBirthday;
	private String beneficiaryPhoto;
	private String fundraisingWillUse;
}
