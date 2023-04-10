package com.srp.constelinkfundraising.db.dto.request;

import java.time.LocalDateTime;

import com.srp.constelinkfundraising.db.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MakeFundraisingRequest {

	private Long beneficiaryId;

	private Long categoryId;

	private int fundraisingAmountGoal;

	private Long fundraisingEndTime;

	private String fundraisingTitle;

	private String fundraisingStory;

	private String fundraisingThumbnail;
	private String fundraisingWillUse;

}
