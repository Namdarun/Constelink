package com.srp.constelinkbeneficiary.db.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.srp.constelinkbeneficiary.db.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	Optional<Hospital> findHospitalById(Long id);

	Page<Hospital> findAll(Pageable pageable);
}
