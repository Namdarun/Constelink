package com.srp.constelinkmember.dto;

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
public class LoginInfoDto {
	private String accessToken;
	private String refreshToken;
	private String nickname;
	private String profile;
	private Role role;
}
