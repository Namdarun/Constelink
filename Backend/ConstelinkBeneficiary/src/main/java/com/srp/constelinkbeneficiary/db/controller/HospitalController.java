package com.srp.constelinkbeneficiary.db.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkbeneficiary.db.dto.enums.HospitalSortType;
import com.srp.constelinkbeneficiary.db.dto.response.HospitalInfoResponse;
import com.srp.constelinkbeneficiary.db.entity.Hospital;
import com.srp.constelinkbeneficiary.db.service.HospitalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "병원 API", description = "병원 API")
@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

	private final HospitalService hospitalService;

	@Operation(summary = "hospitalId로 병원 조회", description = "hospitalId = 병원 Id")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<HospitalInfoResponse> findHospital(@PathVariable("hospitalId") Long id) {
		HospitalInfoResponse hospitalInfoResponse = hospitalService.findHospitalById(id);
		return ResponseEntity.ok(hospitalInfoResponse);
	}

	@Operation(summary = "병원 목록 조회", description = "page = 페이지, "
		+ "size = 한 페이지에 넣을 개수, "
		+ "sortBy = 솔트 타입")
	@GetMapping("")
	public ResponseEntity<Page<Hospital>> getHospitalPage(
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@RequestParam(value = "size", required = false, defaultValue = "5") int size,
		@RequestParam(value = "sortBy", required = false, defaultValue = "ID_ASC") HospitalSortType sortType) {

		Page<Hospital> hospitalPage = hospitalService.hospitalInfoList(page - 1, size, sortType);
		return ResponseEntity.ok(hospitalPage);
	}

}
