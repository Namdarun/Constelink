package com.srp.constelinkmember.dto.response;

import com.srp.constelinkmember.dto.enums.Role;

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
public class LoginResponse {
	private String nickname;

	private Role role;
	private String profile;
}
