package com.srp.constelinkmember.dto.response;

import com.srp.constelinkmember.dto.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoResponse {

	private String name;
	private int totalAmount;
	private int totalFundCnt;
	private Role role;

}
