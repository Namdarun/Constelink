package com.srp.constelinkmember.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoReadyResponse {
	private String tid;
	private String next_redirect_pc_url;
	private String created_at;
}
