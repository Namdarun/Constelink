package com.srp.constelinkmember.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.srp.constelinkmember.db.entity.Donation;
import com.srp.constelinkmember.db.entity.Member;
import com.srp.constelinkmember.db.repository.DonationRepository;
import com.srp.constelinkmember.db.repository.MemberRepository;
import com.srp.constelinkmember.dto.DonationDetailDto;
import com.srp.constelinkmember.dto.request.SaveDonationRequest;
import com.srp.constelinkmember.dto.response.BeneficiaryResponse;
import com.srp.constelinkmember.dto.response.DonationDetailsResponse;
import com.srp.constelinkmember.dto.response.StatsDataResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonationService {

	private final DonationRepository donationRepository;
	private final MemberRepository memberRepository;
	private final RestTemplate restTemplate;

	@Transactional
	public void saveDonation(SaveDonationRequest saveDonationRequest, Long memberId) {

		Donation donation = Donation.builder()
			.memberId(memberId)
			.fundraisingId(saveDonationRequest.getFundraisingId())
			.donationPrice(saveDonationRequest.getDonationPrice())
			.donationTime(LocalDateTime.now())
			.donationTransactionHash(saveDonationRequest.getDonationTransactionHash())
			.hospitalName(saveDonationRequest.getHospitalName())
			.beneficiaryId(saveDonationRequest.getBeneficiary_id())
			.beneficiaryName(saveDonationRequest.getBeneficiaryName())
			.beneficiaryDisease(saveDonationRequest.getBeneficiaryDisease())
			.fundraisingTitle(saveDonationRequest.getFundraisingTitle())
			.fundraisingThumbnail(saveDonationRequest.getFundraisingThumbnail())
			.build();
		donationRepository.save(donation);

		String requestBody = "{\"fundraisingId\":" + saveDonationRequest.getFundraisingId() + ", \"cash\":"
			+ saveDonationRequest.getDonationPrice() + "}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		log.info("기부내역 저장까지 완료 ---  resttemplate 요청 필요");
		String response = restTemplate.postForObject("https://j8a206.p.ssafy.io/fundraising/fundraisings/donate", request,
			String.class);
		log.info("응답 도착 === " + response);

		Optional<Member> findMember = memberRepository.findById(memberId);
		findMember.get().addAmountRaised(donation.getDonationPrice());
		findMember.get().addPoint(donation.getDonationPrice());

	}

	public DonationDetailsResponse listDonation(Long memberId, int page) {
		PageRequest pageRequest = PageRequest.of(page, 8,
			Sort.by(Sort.Direction.DESC, "donationTime"));
		Page<Map<String, Object>> donations = donationRepository.findByMemberId(memberId, pageRequest);
		List<DonationDetailDto> donationDetails = donations.getContent().stream().map(donation ->
			new DonationDetailDto().builder()
				.hospitalName((String)donation.get("hospitalName"))
				.beneficiaryDisease((String)donation.get("beneficiaryDisease"))
				.beneficiaryName((String)donation.get("beneficiaryName"))
				.totalDonationPrice(Integer.parseInt(String.valueOf(donation.get("totalDonationPrice"))))
				.lastDonationTime((LocalDateTime)donation.get("lastDonationTime"))
				.build()).collect(Collectors.toList());

		DonationDetailsResponse donationDetailsResponse = DonationDetailsResponse.builder()
			.donations(donationDetails)
			.totalElements(donations.getTotalElements())
			.totalPages(donations.getTotalPages())
			.build();

		return donationDetailsResponse;
	}

	public StatsDataResponse getStatsData() {
		Map<String, Long> statsData = donationRepository.statsData();
		StatsDataResponse statsDataResponse = StatsDataResponse.builder()
			.allDonation(statsData.get("allDonation").intValue())
			.allHospital(statsData.get("allHospital").intValue())
			.allMember(statsData.get("allMember").intValue())
			.build();
		return statsDataResponse;
	}

	public BeneficiaryResponse getBeneficiaryIds(Long memberId) {
		List<Long> beneficiaryIds = donationRepository.getBeneficiaryIds(memberId);
		BeneficiaryResponse beneficiaryResponse = BeneficiaryResponse.builder()
			.beneficiaryIds(beneficiaryIds)
			.build();

		return beneficiaryResponse;
	}
}
