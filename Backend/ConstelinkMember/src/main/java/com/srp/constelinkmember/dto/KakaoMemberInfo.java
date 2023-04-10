package com.srp.constelinkmember.dto;

import java.util.Map;

import com.srp.constelinkmember.dto.enums.SocialType;

public class KakaoMemberInfo extends OAuth2MemberInfo {
	private Map<String, Object> profile_item;
	private Map<String, Object> properties;

	public KakaoMemberInfo(Map<String, Object> attributes) {
		super(attributes);
		this.properties = (Map<String, Object>)attributes.get("properties");
		this.profile_item = (Map<String, Object>)attributes.get("kakao_account");
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public SocialType getProvider() {
		return SocialType.KAKAO;
	}

	@Override
	public String getEmail() {
		return (String)profile_item.get("email");
	}

	@Override
	public String getNickName() {
		return (String)properties.get("nickname");
	}

	@Override
	public String getProfile() {
		return (String)properties.get("profile_image");
	}
}
