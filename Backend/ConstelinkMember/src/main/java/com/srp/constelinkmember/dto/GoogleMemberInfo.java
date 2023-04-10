package com.srp.constelinkmember.dto;

import java.util.Map;

import com.srp.constelinkmember.dto.enums.SocialType;

public class GoogleMemberInfo extends OAuth2MemberInfo {

	public GoogleMemberInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getProviderId() {
		return (String)attributes.get("sub");
	}

	@Override
	public SocialType getProvider() {
		return SocialType.GOOGLE;
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getNickName() {
		return (String)attributes.get("name");
	}

	@Override
	public String getProfile() {
		return (String)attributes.get("picture");
	}
}
