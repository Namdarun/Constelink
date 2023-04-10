package com.srp.constelinkmember.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.srp.constelinkmember.dto.request.KakaoPayRequest;
import com.srp.constelinkmember.dto.response.KakaoApproveResponse;
import com.srp.constelinkmember.dto.response.KakaoReadyResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KakaoPayService {

	static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
	@Value("${kakao.admin.key}")
	private String admin_Key;
	@Value("${kakao.base.fail-url}")
	private String failUrl;

	@Value("${kakao.base.success-url}")
	private String successUrl;

	@Value("${kakao.base.cancel-url}")
	private String cancelUrl;
	private KakaoReadyResponse kakaoReady;

	private final String oreder_id = "patient_donation";

	public KakaoReadyResponse kakaoPayReady(KakaoPayRequest kakaoPayRequest) {

		// 카카오페이 요청 양식
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", cid);
		parameters.add("partner_order_id", oreder_id);
		parameters.add("partner_user_id", "constelink");
		parameters.add("item_name", kakaoPayRequest.getItemName());
		parameters.add("quantity", "1");
		parameters.add("total_amount", kakaoPayRequest.getAmount());
		parameters.add("vat_amount", "0");
		parameters.add("tax_free_amount", "1");
		parameters.add("approval_url", successUrl); // 성공 시 redirect url
		parameters.add("cancel_url", cancelUrl); // 취소 시 redirect url
		parameters.add("fail_url", failUrl); // 실패 시 redirect url

		// 파라미터, 헤더
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		// 외부에 보낼 url
		RestTemplate restTemplate = new RestTemplate();

		kakaoReady = restTemplate.postForObject(
			"https://kapi.kakao.com/v1/payment/ready",
			requestEntity,
			KakaoReadyResponse.class);

		return kakaoReady;
	}

	public KakaoApproveResponse approveResponse(String pgToken) {

		// 카카오 요청
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", cid);
		parameters.add("tid", kakaoReady.getTid());
		parameters.add("partner_order_id", oreder_id);
		parameters.add("partner_user_id", "constelink");
		parameters.add("pg_token", pgToken);

		// 파라미터, 헤더
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		// 외부에 보낼 url
		RestTemplate restTemplate = new RestTemplate();

		KakaoApproveResponse approveResponse = restTemplate.postForObject(
			"https://kapi.kakao.com/v1/payment/approve",
			requestEntity,
			KakaoApproveResponse.class);

		return approveResponse;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();

		String auth = "KakaoAK " + admin_Key;

		httpHeaders.set("Authorization", auth);
		httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		return httpHeaders;
	}

}
