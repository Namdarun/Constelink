package com.srp.constelinkmember.dto;

import java.util.Map;

import com.srp.constelinkmember.dto.enums.SocialType;

public abstract class OAuth2MemberInfo {
	protected Map<String, Object> attributes;

	public OAuth2MemberInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public abstract String getProviderId();

	public abstract SocialType getProvider();

	public abstract String getEmail();

	public abstract String getNickName();

	public abstract String getProfile();
}
