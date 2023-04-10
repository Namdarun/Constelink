package com.srp.constelinkmember.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srp.constelinkmember.db.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findBySocialId(String socialId);

}
