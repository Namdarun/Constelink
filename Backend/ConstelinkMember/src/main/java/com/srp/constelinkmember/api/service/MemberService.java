package com.srp.constelinkmember.api.service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srp.constelinkmember.HospitalInfoRes;
import com.srp.constelinkmember.common.exception.CustomException;
import com.srp.constelinkmember.common.exception.CustomExceptionType;
import com.srp.constelinkmember.db.entity.Member;
import com.srp.constelinkmember.db.repository.DonationRepository;
import com.srp.constelinkmember.db.repository.MemberRepository;
import com.srp.constelinkmember.dto.enums.Role;
import com.srp.constelinkmember.dto.request.ModifyMemberInfoRequest;
import com.srp.constelinkmember.dto.response.MemberInfoResponse;
import com.srp.constelinkmember.grpc.service.HospitalGrpcClientService;
import com.srp.constelinkmember.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final DonationRepository donationRepository;
	private final HospitalGrpcClientService hospitalGrpcClientService;
	private final TokenProvider tokenProvider;
	private final RedisTemplate<String, String> redisTemplate;

	@Transactional
	public void withdrawal(String accessToken, String refreshToken) {
		String getTokenSub = tokenProvider.resolveToken(accessToken);
		Long memberId = Long.valueOf(getTokenSub);
		Optional<Member> findMember = memberRepository.findById(memberId);
		if (!findMember.isPresent()) {
			throw new CustomException(CustomExceptionType.USER_NOT_FOUND);
		}
		findMember.get().setMemberInactive(true);
		String redisKey = "RT:" + refreshToken;
		redisTemplate.delete(redisKey);
		long expiration = tokenProvider.getExpiration(accessToken);
		Date now = new Date();
		redisTemplate.opsForValue().set(accessToken, accessToken, expiration - now.getTime());
	}

	public MemberInfoResponse getMemberInfo(String accessToken) {
		String roleByToken = tokenProvider.getRoleByToken(accessToken);
		String id = tokenProvider.resolveToken(accessToken);
		Long memberId = Long.valueOf(id);
		log.info("Role == " + roleByToken);
		MemberInfoResponse response = new MemberInfoResponse();

		if (roleByToken.equals("MEMBER")) {
			log.info("MEMBER 입니다");
			Map<String, Object> donationInfo = donationRepository.getDonationInfo(memberId);
			Optional<Member> findMember = memberRepository.findById(memberId);
			String tf = String.valueOf(donationInfo.get("totalFundCount"));
			log.info("tf === " + tf);
			String td = String.valueOf(donationInfo.get("totalDonationPrice"));
			if (td == null) {
				td = "0";
			}
			log.info("td == " + td);
			int totalFundCount = Integer.parseInt(tf);
			int totalDonationPrice = Integer.parseInt(td);

			response.setName(findMember.get().getUsername());
			response.setTotalAmount(totalDonationPrice);
			response.setTotalFundCnt(totalFundCount);
			response.setRole(Role.MEMBER);
		} else if (roleByToken.equals("HOSPITAL")) {
			log.info("HOSPITAL 임");
			HospitalInfoRes res = hospitalGrpcClientService.getHospitalInfo(memberId);
			response.setName(res.getName());
			response.setRole(Role.HOSPITAL);
		} else if (roleByToken.equals("ADMIN")) {
			log.info("ADMIN 이무니다");
			Optional<Member> findMember = memberRepository.findById(memberId);
			response.setName(findMember.get().getUsername());
			response.setRole(Role.ADMIN);
		}
		return response;
	}

	@Transactional
	public void modifyMemberInfo(ModifyMemberInfoRequest modifyRequest, String accessToken) {
		String id = tokenProvider.resolveToken(accessToken);
		Long memberId = Long.valueOf(id);

		Optional<Member> findMember = memberRepository.findById(memberId);
		if (findMember.isPresent()) {
			Member member = findMember.get();
			if (!modifyRequest.getNickname().equals("")) {
				member.setUsername(modifyRequest.getNickname());
			}
			if (!modifyRequest.getProfileImg().equals("")) {
				{
					member.setMemberProfileImg(modifyRequest.getProfileImg());
				}
			}
		} else {
			throw new CustomException(CustomExceptionType.USER_NOT_FOUND);
		}
	}
}
