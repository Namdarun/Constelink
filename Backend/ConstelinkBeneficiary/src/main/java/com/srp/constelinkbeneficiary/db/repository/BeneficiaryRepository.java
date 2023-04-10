package com.srp.constelinkbeneficiary.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.srp.constelinkbeneficiary.db.entity.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
	@Override
	<S extends Beneficiary> S saveAndFlush(S entity);

	@Query("SELECT u from Beneficiary u join fetch Hospital h on u.hospital.id = h.id where u.id = :id")
	Optional<Beneficiary> findBeneficiaryById(Long id);

	// Optional<Beneficiary> getBeneficiariesById(Long id);

	@Query(value = "SELECT u from Beneficiary u join fetch Hospital h on u.hospital.id = h.id where u.hospital.id = :hospitalId",countQuery = "SELECT count(u) from Beneficiary u where u.hospital.id = :hospitalId")
	Page<Beneficiary> findBeneficiariesByHospitalId(Long hospitalId, Pageable pageable);

	@Query(value = "SELECT u from Beneficiary u join fetch Hospital h on u.hospital.id = h.id",countQuery = "SELECT count(u) from Beneficiary u")
	Page<Beneficiary> findAll(Pageable pageable);

	List<Beneficiary> findAllByIdIn(List<Long> idList);

	// @Query("select distinct(u.id) as beneficiaryId, u.beneficiaryName as beneficiaryName "
	// 	+ ", u.beneficiaryPhoto as beneficiaryPhoto, u.beneficiaryDisease as beneficiaryDisease "
	// 	+ ", h.hospitalName as hospitalName "
	// 	+ "from Beneficiary u join RecoveryDiary r "
	// 	+ "on r.beneficiary.id = u.id "
	// 	+ "join Hospital h on h.id = u.hospital.id "
	// 	+ "group by u.id "
	// 	+ "order by max(r.recoveryDiaryRegdate)")
	// @Query("select b from Beneficiary b join RecoveryDiary r on r.beneficiary.id = b.id join Hospital h on h.id=b.hospital.id  group by b.id order by max(r.recoveryDiaryRegdate)")
	@Query("select b, max(r.recoveryDiaryRegdate), r, h as recoveryDiaryRegdate"
		+ " from Beneficiary b"
		+ " left join fetch RecoveryDiary r on r.beneficiary.id = b.id"
		+ " left join fetch Hospital h on b.hospital.id = h.id"
		+ " group by b.id")
	Page<Object[]> findAlltoPage(Pageable pageable);

	@Query("select b, max(r.recoveryDiaryRegdate), r, h as recoveryDiaryRegdate"
		+ " from RecoveryDiary r"
		+ " join Beneficiary b on r.beneficiary.id = b.id"
		+ " join Hospital h on b.hospital.id = h.id"
		+ " group by b.id")
	Page<Object[]> findAlltoPageByRegdate(Pageable pageable);

	@Query("select b, max(r.recoveryDiaryRegdate) as recoveryDiaryRegdate"
		+ " from RecoveryDiary r"
		+ " join Beneficiary b on r.beneficiary.id = b.id"
		+ " join Hospital h on b.hospital.id = h.id"
		+ " where b.id in :idList"
		+ " group by b.id")
	Page<Object[]> findBeneficiariesByIdIsIn(List<Long> idList, Pageable pageable);
}
