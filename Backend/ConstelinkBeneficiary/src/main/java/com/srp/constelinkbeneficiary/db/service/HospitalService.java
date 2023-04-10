package com.srp.constelinkbeneficiary.db.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srp.constelinkbeneficiary.common.exception.CustomException;
import com.srp.constelinkbeneficiary.common.exception.CustomExceptionType;
import com.srp.constelinkbeneficiary.db.dto.enums.HospitalSortType;
import com.srp.constelinkbeneficiary.db.dto.response.HospitalInfoResponse;
import com.srp.constelinkbeneficiary.db.entity.Hospital;
import com.srp.constelinkbeneficiary.db.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {

	private final HospitalRepository hospitalRepository;

	public HospitalInfoResponse findHospitalById(Long id) {
		// 병원 존재 확인
		Hospital hospital = hospitalRepository.findHospitalById(id)
			.orElseThrow(() -> new CustomException(CustomExceptionType.HOSPITAL_NOT_FOUND));

		HospitalInfoResponse hospitalInfoResponse
			= HospitalInfoResponse.builder()
			.hospitalLink(hospital.getHospitalLink())
			.hospitalName(hospital.getHospitalName())
			.hospitalWalletAddress(hospital.getHospitalWalletAddress())
			.hospitalTotalBeneficiary(hospital.getHospitalTotalBeneficiary())
			.hospitalTotalAmountRaised(hospital.getHospitalTotalAmountRaised())
			.hospitalId(hospital.getId())
			.build();
		return hospitalInfoResponse;
	}

	public Page<Hospital> hospitalInfoList(int page, int size, HospitalSortType sortBy) {
		Page<Hospital> ResponseHospitalInfoList;
		// 0이면 오름차순+
		switch (sortBy) {
			case NONE:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size));
				break;
			case ID_ASC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("id").ascending()));
				break;
			case ID_DESC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("id").descending()));
				break;
			case NAME_ASC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("hospitalName").ascending()));
				break;
			case NAME_DESC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("hospitalName").descending()));
				break;
			case BENEFICIARIES_ASC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("hospitalTotalBeneficiary").ascending()));
				break;
			case BENEFICIARIES_DESC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("hospitalTotalBeneficiary").descending()));
				break;
			case FUND_RAISED_ASC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("hospitalTotalAmountRaised").ascending()));
				break;
			case FUND_RAISED_DESC:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("hospitalTotalAmountRaised").descending()));
				break;
			default:
				ResponseHospitalInfoList = hospitalRepository.findAll(
					PageRequest.of(page, size, Sort.by("id").ascending()));
				break;
		}

		return ResponseHospitalInfoList;
	}

}
