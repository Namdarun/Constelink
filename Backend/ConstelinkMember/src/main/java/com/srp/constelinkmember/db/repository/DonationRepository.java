package com.srp.constelinkmember.db.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srp.constelinkmember.db.entity.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

	@Query("select count(distinct d.fundraisingId) as totalFundCount, sum(d.donationPrice) as totalDonationPrice "
		+ "from Donation d where d.memberId = :memberId")
	Map<String, Object> getDonationInfo(@Param("memberId") Long memberId);

	@Query("select d.beneficiaryId as beneficiaryId,d.beneficiaryName as beneficiaryName"
		+ ", d.beneficiaryDisease as beneficiaryDisease, d.hospitalName as hospitalName, "
		+ "sum(d.donationPrice) as totalDonationPrice, max(d.donationTime) as lastDonationTime"
		+ " from Donation d where d.memberId = :memberId"
		+ " group by d.beneficiaryId, d.beneficiaryName, d.beneficiaryDisease, d.hospitalName ")
	Page<Map<String, Object>> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

	@Query(value = "SELECT COUNT(*) AS allDonation, " +
		"(SELECT COUNT(*) FROM Member WHERE role = 'MEMBER') AS allMember, " +
		"(SELECT COUNT(*) FROM Member WHERE role = 'HOSPITAL') AS allHospital " +
		"FROM Donation")
	Map<String, Long> statsData();

	@Query("select distinct(d.beneficiaryId) as beneficiaryId from Donation d where d.memberId = :memberId")
	List<Long> getBeneficiaryIds(@Param("memberId") Long memberId);

}
