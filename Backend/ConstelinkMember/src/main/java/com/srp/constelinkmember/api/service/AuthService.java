package com.srp.constelinkmember.api.service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srp.constelinkmember.common.exception.CustomException;
import com.srp.constelinkmember.common.exception.CustomExceptionType;
import com.srp.constelinkmember.db.entity.Member;
import com.srp.constelinkmember.db.repository.MemberRepository;
import com.srp.constelinkmember.dto.LoginInfoDto;
import com.srp.constelinkmember.dto.enums.Role;
import com.srp.constelinkmember.dto.request.LoginRequest;
import com.srp.constelinkmember.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {
	private final MemberRepository memberRepository;

	private final RedisTemplate<String, String> redisTemplate;
	private final TokenProvider tokenProvider;

	@Transactional
	public LoginInfoDto login(LoginRequest loginRequest) {
		String key = loginRequest.getKey();
		log.info("key == " + key);

		Long memberId = Long.valueOf(redisTemplate.opsForValue().get(key));

		if (memberId == null) {
			throw new CustomException(CustomExceptionType.ABNORMAL_ACCESS_EXCEPTION);
		}

		Optional<Member> findMember = memberRepository.findById(memberId);

		LoginInfoDto loginInfoDto;

		if (findMember.isPresent()) {
			Member member = findMember.get();
			if (loginRequest.isFlag()) {
				member.setMemberInactive(false);
			}
			String accessToken = tokenProvider.createAccessToken(member.getId(), member.getRole());
			String refreshToken = tokenProvider.createRefreshToken();
			String redisKey = "RT:" + refreshToken;

			redisTemplate.opsForValue().set(redisKey, Long.toString(member.getId()), 7, TimeUnit.DAYS);
			loginInfoDto = LoginInfoDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.nickname(member.getUsername())
				.profile(member.getMemberProfileImg())
				.role(member.getRole())
				.build();
		} else {
			throw new CustomException(CustomExceptionType.USER_NOT_FOUND);
		}

		return loginInfoDto;
	}

	public String reGenerateToken(String refreshToken) {
		String redisKey = "RT:" + refreshToken;

		String saveMemberId = redisTemplate.opsForValue().get(redisKey);
		if (saveMemberId == null) {
			throw new CustomException(CustomExceptionType.NOT_LOGINED_EXCEPTION);
		}
		Optional<Member> findMember = memberRepository.findById(Long.valueOf(saveMemberId));
		Role role = findMember.get().getRole();
		String accessToken = tokenProvider.createAccessToken(Long.valueOf(saveMemberId), role);
		return accessToken;

	}

	public void logout(String accessToken, String refreshToken) {
		String redisKey = "RT:" + refreshToken;
		Boolean isDelete = redisTemplate.delete(redisKey);
		long expiration = tokenProvider.getExpiration(accessToken);
		Date now = new Date();
		redisTemplate.opsForValue().set(accessToken, accessToken, expiration - now.getTime());
	}
}
