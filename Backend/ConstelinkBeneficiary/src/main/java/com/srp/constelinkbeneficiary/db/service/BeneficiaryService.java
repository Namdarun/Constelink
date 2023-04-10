package com.srp.constelinkbeneficiary.db.service;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.srp.constelinkbeneficiary.common.exception.CustomException;
import com.srp.constelinkbeneficiary.common.exception.CustomExceptionType;
import com.srp.constelinkbeneficiary.db.dto.common.BeneficiariesByRegDateDTO;
import com.srp.constelinkbeneficiary.db.dto.common.GetBeneficiaryIds;
import com.srp.constelinkbeneficiary.db.dto.enums.BeneficiaryMemberDonate;
import com.srp.constelinkbeneficiary.db.dto.enums.BeneficiarySortType;
import com.srp.constelinkbeneficiary.db.dto.enums.RecoveryDiaryMemberDonatedType;
import com.srp.constelinkbeneficiary.db.dto.request.BeneficiaryEditRequest;
import com.srp.constelinkbeneficiary.db.dto.request.BeneficiaryReqeust;
import com.srp.constelinkbeneficiary.db.dto.response.BeneficiaryByDiaryDateResponse;
import com.srp.constelinkbeneficiary.db.dto.response.BeneficiaryInfoResponse;
import com.srp.constelinkbeneficiary.db.dto.response.RecoveryDiaryResponse;
import com.srp.constelinkbeneficiary.db.entity.Beneficiary;
import com.srp.constelinkbeneficiary.db.entity.Hospital;
import com.srp.constelinkbeneficiary.db.entity.RecoveryDiary;
import com.srp.constelinkbeneficiary.db.repository.BeneficiaryRepository;
import com.srp.constelinkbeneficiary.db.repository.HospitalRepository;
import com.srp.constelinkbeneficiary.db.repository.RecoveryDiaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BeneficiaryService {

	private final BeneficiaryRepository beneficiaryRepository;
	private final HospitalRepository hospitalRepository;
	private final RecoveryDiaryRepository recoveryDiaryRepository;
	private final RestTemplate restTemplate;

	public BeneficiaryInfoResponse findBeneficiaryById(Long id) {
		Beneficiary beneficiary = beneficiaryRepository.findBeneficiaryById(id)
			.orElseThrow(() -> new CustomException(CustomExceptionType.BENEFICIARY_NOT_FOUND));
		BeneficiaryInfoResponse beneficiaryInfoDto = BeneficiaryInfoResponse.builder()
			.beneficiaryDisease(beneficiary.getBeneficiaryDisease())
			.beneficiaryAmountGoal(beneficiary.getBeneficiaryAmountGoal())
			.beneficiaryAmountRaised(beneficiary.getBeneficiaryAmountRaised())
			.beneficiaryName(beneficiary.getBeneficiaryName())
			.beneficiaryPhoto(beneficiary.getBeneficiaryPhoto())
			.beneficiaryBirthday(beneficiary.getBeneficiaryBirthday().getTime())
			.hospitalId(beneficiary.getHospital().getId())
			.hospitalName(beneficiary.getHospital().getHospitalName())
			.hospitalLink(beneficiary.getHospital().getHospitalLink())
			.beneficiaryId(beneficiary.getId())
			.beneficiaryStatus(beneficiary.getBeneficiaryStatus())
			.build();
		return beneficiaryInfoDto;
	}

	public Page<BeneficiaryInfoResponse> findBeneficiariesByHospitalId(Long hospitalId, int page, int size) {
		Page<Beneficiary> beneficiaryPage = beneficiaryRepository.findBeneficiariesByHospitalId(hospitalId,
			PageRequest.of(page, size));
		Page<BeneficiaryInfoResponse> beneficiaryInfoList = beneficiaryPage.map(m -> BeneficiaryInfoResponse.builder()
			.beneficiaryName(m.getBeneficiaryName())
			.beneficiaryBirthday(m.getBeneficiaryBirthday().getTime())
			.beneficiaryPhoto(m.getBeneficiaryPhoto())
			.beneficiaryAmountRaised(m.getBeneficiaryAmountRaised())
			.beneficiaryAmountGoal(m.getBeneficiaryAmountGoal())
			.beneficiaryDisease(m.getBeneficiaryDisease())
			.hospitalLink(m.getHospital().getHospitalLink())
			.hospitalId(m.getHospital().getId())
			.hospitalName(m.getHospital().getHospitalName())
			.beneficiaryId(m.getId())
			.diaryFinishedDate(null)
			.beneficiaryStatus(m.getBeneficiaryStatus())
			.build()
		);
		return beneficiaryInfoList;
	}

	@Transactional
	public BeneficiaryInfoResponse addBeneficiary(BeneficiaryReqeust beneficiaryReqeust, Long hospitalId) {
		Beneficiary beneficiary = new Beneficiary().builder()
			.hospital(hospitalRepository.findHospitalById(hospitalId)
				.orElseThrow(() -> new CustomException(
					CustomExceptionType.HOSPITAL_NOT_FOUND)))
			.beneficiaryName(beneficiaryReqeust.getBeneficiaryName())
			.beneficiaryDisease(beneficiaryReqeust.getBeneficiaryDisease())
			.beneficiaryPhoto(beneficiaryReqeust.getBeneficiaryPhoto())
			.beneficiaryAmountGoal(beneficiaryReqeust.getBeneficiaryAmountGoal())
			.beneficiaryStatus("RAISING")
			.beneficiaryBirthday(beneficiaryReqeust.getBeneficiaryBirthday())
			.build();
		beneficiary = beneficiaryRepository.saveAndFlush(beneficiary);

		BeneficiaryInfoResponse beneficiaryInfoDto = BeneficiaryInfoResponse.builder()
			.beneficiaryDisease(beneficiary.getBeneficiaryDisease())
			.beneficiaryAmountGoal(beneficiary.getBeneficiaryAmountGoal())
			.beneficiaryAmountRaised(beneficiary.getBeneficiaryAmountRaised())
			.beneficiaryName(beneficiary.getBeneficiaryName())
			.beneficiaryPhoto(beneficiary.getBeneficiaryPhoto())
			.beneficiaryBirthday(beneficiary.getBeneficiaryBirthday().getTime())
			.beneficiaryId(beneficiary.getId())
			.hospitalName(beneficiary.getHospital().getHospitalName())
			.hospitalId(beneficiary.getHospital().getId())
			.hospitalLink(beneficiary.getHospital().getHospitalLink())
			.beneficiaryId(beneficiary.getId())
			.beneficiaryStatus(beneficiary.getBeneficiaryStatus())
			.build();
		return beneficiaryInfoDto;
	}

	public Page<BeneficiaryInfoResponse> findAllBeneficiary(int page, int size, BeneficiarySortType beneficiarySortType) {

		Page<Object[]> beneficiariesByRegDateDTOPage;

		switch (beneficiarySortType) {
			case NONE:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("id").ascending()));
				break;
			case NAME_ASC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("beneficiaryName").ascending()));
				break;
			case NAME_DESC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("beneficiaryName").descending()));
				break;
			case AGE_ASC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("beneficiaryBirthday").ascending()));
				break;
			case AGE_DESC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("beneficiaryBirthday").descending()));
				break;
			case FUND_RAISED_ASC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("beneficiaryAmountRaised").ascending()));
				break;
			case FUND_RAISED_DESC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("beneficiaryAmountRaised").descending()));
				break;
			case DIARY_DATE_ASC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPageByRegdate(PageRequest.of(page,size,Sort.by("recoveryDiaryRegdate").ascending()));
				break;
			case DIARY_DATE_DESC:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPageByRegdate(PageRequest.of(page,size,Sort.by("recoveryDiaryRegdate").descending()));
				break;
			default:
				beneficiariesByRegDateDTOPage = beneficiaryRepository.findAlltoPage(PageRequest.of(page, size, Sort.by("id").ascending()));
				break;
		}
		Page<BeneficiaryInfoResponse> beneficiaryInfoResponsePage = beneficiariesByRegDateDTOPage.map(item -> {
			Beneficiary beneficiary = (Beneficiary)item[0];
			LocalDateTime time = (LocalDateTime)item[1];
				return BeneficiaryInfoResponse.builder()
					.beneficiaryDisease(beneficiary.getBeneficiaryDisease())
					.beneficiaryAmountGoal(beneficiary.getBeneficiaryAmountGoal())
					.beneficiaryAmountRaised(beneficiary.getBeneficiaryAmountRaised())
					.beneficiaryName(beneficiary.getBeneficiaryName())
					.beneficiaryPhoto(beneficiary.getBeneficiaryPhoto())
					.beneficiaryBirthday(beneficiary.getBeneficiaryBirthday().getTime())
					.hospitalId(beneficiary.getHospital().getId())
					.hospitalName(beneficiary.getHospital().getHospitalName())
					.hospitalLink(beneficiary.getHospital().getHospitalLink())
					.beneficiaryId(beneficiary.getId())
					.diaryFinishedDate(time!=null?time.atZone(ZoneId.of("Asia/Seoul"))
						.toInstant()
						.toEpochMilli():null)
					.beneficiaryStatus(beneficiary.getBeneficiaryStatus())
					.build();
			});

		return beneficiaryInfoResponsePage;
	}


	public Page<BeneficiaryByDiaryDateResponse> getBeneficiaryDonatedByMember(int page, int size, Long memberId,
		BeneficiaryMemberDonate sortType) {
		Page<Object[]> beneficiaryPage;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = UriComponentsBuilder
			.fromUriString("http://j8a206.p.ssafy.io:8997")
			.path("/donations/beneficiaryids")
			//쿼리파람을 사용할 수 있다.
			.queryParam("id", memberId)
			.encode()
			.build()
			.toUri();
		;
		GetBeneficiaryIds response = restTemplate.getForObject(uri, GetBeneficiaryIds.class);

		switch (sortType) {
			case NONE:
				beneficiaryPage = beneficiaryRepository.findBeneficiariesByIdIsIn(
					response.getBeneficiaryIds()
					, PageRequest.of(page, size));
				break;
			case DATE_ASC:
				beneficiaryPage = beneficiaryRepository.findBeneficiariesByIdIsIn(
					response.getBeneficiaryIds()
					, PageRequest.of(page, size, Sort.by("recoveryDiaryRegdate").ascending()));
				break;
			case DATE_DESC:
				beneficiaryPage = beneficiaryRepository.findBeneficiariesByIdIsIn(
					response.getBeneficiaryIds()
					, PageRequest.of(page, size, Sort.by("recoveryDiaryRegdate").descending()));
				break;
			default:
				beneficiaryPage = beneficiaryRepository.findBeneficiariesByIdIsIn(
					response.getBeneficiaryIds()
					, PageRequest.of(page, size));
				break;
		}


		Page<BeneficiaryByDiaryDateResponse> beneficiaryInfoResponsePage = beneficiaryPage.map(item ->
			{

				Beneficiary beneficiary = (Beneficiary)item[0];
				LocalDateTime time = (LocalDateTime)item[1];
				return BeneficiaryByDiaryDateResponse.builder()
					.beneficiaryDisease(beneficiary.getBeneficiaryDisease())
					.beneficiaryAmountGoal(beneficiary.getBeneficiaryAmountGoal())
					.beneficiaryAmountRaised(beneficiary.getBeneficiaryAmountRaised())
					.beneficiaryName(beneficiary.getBeneficiaryName())
					.beneficiaryPhoto(beneficiary.getBeneficiaryPhoto())
					.beneficiaryBirthday(beneficiary.getBeneficiaryBirthday().getTime())
					.hospitalId(beneficiary.getHospital().getId())
					.hospitalName(beneficiary.getHospital().getHospitalName())
					.hospitalLink(beneficiary.getHospital().getHospitalLink())
					.beneficiaryId(beneficiary.getId())
					.diaryFinishedDate(time.atZone(ZoneId.of("Asia/Seoul"))
						.toInstant()
						.toEpochMilli())
					.build();
			}
		);

		return beneficiaryInfoResponsePage;
	}

	@Transactional
	public Long editBeneficiary(BeneficiaryEditRequest beneficiaryEditRequest, Long beneficiaryId, Long hospitalId) {
		Beneficiary beneficiary = beneficiaryRepository.findBeneficiaryById(beneficiaryId)
			.orElseThrow(()->new CustomException(CustomExceptionType.BENEFICIARY_NOT_FOUND));
		if(hospitalId != beneficiary.getHospital().getId()){
			throw new CustomException(CustomExceptionType.HOSPITAL_AUTHORIZATION_ERROR);
		}
		if(beneficiaryEditRequest.getBeneficiaryStatus().name()==beneficiary.getBeneficiaryStatus()) {
			return beneficiary.getId();
		}
		beneficiary.setBeneficiaryStatus(beneficiaryEditRequest.getBeneficiaryStatus().name());
		beneficiaryRepository.saveAndFlush(beneficiary);
		return beneficiary.getId();
	}

}
