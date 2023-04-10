package com.srp.constelinkfundraising.db.repository;

import java.util.HashSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srp.constelinkfundraising.db.entity.Bookmark;
import com.srp.constelinkfundraising.db.entity.BookmarkId;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

	Bookmark findBookmarkById(BookmarkId bookmarkId);

	// Page<Bookmark> findBookmarksByIdMemberId(Long memberId, Pageable pageable);

	@Query("SELECT u.id.fundraisingId FROM Bookmark u WHERE u.id.memberId=:memberId")
	HashSet<Long> findBookmarksByIdMemberId(Long memberId);

	@Query(value = "SELECT u FROM Bookmark u JOIN FETCH u.fundraising JOIN FETCH u.fundraising.category WHERE u.id.memberId = :memberId", countQuery = "SELECT count(u) FROM Bookmark u WHERE u.id.memberId = :memberId")
	Page<Bookmark> findBookmarksByIdMemberIdForRead(@Param(value = "memberId") Long memberId, Pageable pageable);
	// @Query("SELECT u.id.fundraisingId FROM Bookmark u")
	// HashSet<Long> findBookmarksByIdMemberId(Long memberId, Pageable pageable);

}
