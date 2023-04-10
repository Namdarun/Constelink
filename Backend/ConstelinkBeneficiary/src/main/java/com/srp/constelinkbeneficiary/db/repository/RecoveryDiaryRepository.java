package com.srp.constelinkbeneficiary.db.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.srp.constelinkbeneficiary.db.dto.common.BeneficiariesByRegDateDTO;
import com.srp.constelinkbeneficiary.db.entity.Beneficiary;
import com.srp.constelinkbeneficiary.db.entity.RecoveryDiary;

public interface RecoveryDiaryRepository extends JpaRepository<RecoveryDiary, Long> {

	Page<RecoveryDiary> findAll(Pageable pageable);

	Page<RecoveryDiary> getRecoveryDiariesByBeneficiaryId(Long id, Pageable pageable);


	// @Query(value = "SELECT r from RecoveryDiary r join fetch Beneficiary b on r.beneficiary.id=b.id where r.beneficiary.id in :id")
	Page<RecoveryDiary> getRecoveryDiariesByBeneficiary_IdIsIn(List<Long> id, Pageable pageable);
}
