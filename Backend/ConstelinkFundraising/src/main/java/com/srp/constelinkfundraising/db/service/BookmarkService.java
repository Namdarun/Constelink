package com.srp.constelinkfundraising.db.service;

import java.time.ZoneId;
import java.util.HashSet;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srp.beneficiaryrpc.BeneficiariesInfoReq;
import com.srp.beneficiaryrpc.BeneficiariesInfoRes;
import com.srp.beneficiaryrpc.BeneficiaryGrpcServiceGrpc;
import com.srp.beneficiaryrpc.BeneficiaryInfoRes;
import com.srp.constelinkfundraising.common.exception.CustomException;
import com.srp.constelinkfundraising.common.exception.CustomExceptionType;
import com.srp.constelinkfundraising.db.dto.request.BookmarkFundraisingRequest;
import com.srp.constelinkfundraising.db.dto.response.FundraisingResponse;
import com.srp.constelinkfundraising.db.entity.Bookmark;
import com.srp.constelinkfundraising.db.entity.BookmarkId;
import com.srp.constelinkfundraising.db.repository.BookmarkRepository;
import com.srp.constelinkfundraising.db.repository.CategoryRepository;
import com.srp.constelinkfundraising.db.repository.FundraisingRepository;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {


	private final BookmarkRepository bookmarkRepository;
	private final FundraisingRepository fundraisingRepository;
	private final CategoryRepository categoryRepository;
	private ManagedChannel channel = ManagedChannelBuilder.forAddress(
			"constelink-beneficiary",9090)
		.usePlaintext()
		.build();
	public BeneficiaryGrpcServiceGrpc.BeneficiaryGrpcServiceBlockingStub stub = BeneficiaryGrpcServiceGrpc.newBlockingStub(
		channel);


	@Transactional
	public Boolean bookmarkFundraising(BookmarkFundraisingRequest bookmarkFundraisingRequest, Long memberId) {
		//fundraisingId 검증 필요하면 추가하기(의문)
		if(bookmarkFundraisingRequest.getFundraisingId() < 1) {
			throw new CustomException(CustomExceptionType.FUNDRAISING_NOT_FOUND);
		}
		BookmarkId bookmarkId = new BookmarkId(memberId,
			bookmarkFundraisingRequest.getFundraisingId());
		Bookmark bookmark = bookmarkRepository.findBookmarkById(bookmarkId);

		if (bookmark == null) {
			bookmark = new Bookmark(bookmarkId,
				fundraisingRepository.findFundraisingById(bookmarkId.getFundraisingId()).orElseThrow(()
					-> new CustomException(CustomExceptionType.FUNDRAISING_NOT_FOUND)));
			bookmarkRepository.saveAndFlush(bookmark);
			return true;
		} else {
			bookmarkRepository.deleteById(bookmarkId);
			return false;
		}
	}

	public Page<FundraisingResponse> getBookmarks(Long memberId, int page, int size) {

		// Page<Bookmark> bookmarks = bookmarkRepository.findBookmarksByIdMemberId(memberId, PageRequest.of(page, size));

		Page<Bookmark> bookmarks = bookmarkRepository.findBookmarksByIdMemberIdForRead(memberId, PageRequest.of(page, size));
		// List<Category> categories = categoryRepository.findAll();
		// HashSet<Fundraising> fundraisings = fundraisingRepository.findFundraisingsByIdIsIn(bookmarks.));
		HashSet<Long> idList = new HashSet<>();
		// Map<Long, String> categoriesMap = categories.stream()
		// 	.collect(Collectors.toMap(Category::getId, Category::getCategoryName));

		Page<FundraisingResponse> bookmarkResponses = bookmarks.map(bookmark -> {
			idList.add(bookmark.getFundraising().getBeneficiaryId());
			return new FundraisingResponse().builder()
				.categoryName(bookmark.getFundraising().getCategory().getCategoryName())
				.beneficiaryId(bookmark.getFundraising().getBeneficiaryId())
				.fundraisingAmountGoal(bookmark.getFundraising().getFundraisingAmountGoal())
				.fundraisingEndTime(bookmark.getFundraising()
					.getFundraisingEndTime()
					.atZone(ZoneId.of("Asia/Seoul"))
					.toInstant()
					.toEpochMilli())
				.fundraisingId(bookmark.getFundraising().getId())
				.fundraisingStartTime(bookmark.getFundraising()
					.getFundraisingStartTime()
					.atZone(ZoneId.of("Asia/Seoul"))
					.toInstant()
					.toEpochMilli())
				.fundraisingAmountRaised(bookmark.getFundraising().getFundraisingAmountRaised())
				.fundraisingTitle(bookmark.getFundraising().getFundraisingTitle())
				.fundraisingThumbnail(bookmark.getFundraising().getFundraisingThumbnail())
				.fundraisingStory(bookmark.getFundraising().getFundraisingStory())
				.fundraisingPeople(bookmark.getFundraising().getFundraisingPeople())
				.fundraisingBookmarked(true)
				.fundraisingWillUse(bookmark.getFundraising().getFundraisingWillUse())
				.build();
		});
		BeneficiariesInfoReq beneficiariesInfoReq = BeneficiariesInfoReq.newBuilder()
			.addAllId(idList.stream().toList()).build();
		BeneficiariesInfoRes beneficiariesInfoRes = stub.getBeneficiariesRpc(beneficiariesInfoReq);

		bookmarkResponses.getContent().stream().forEach(item -> {
			BeneficiaryInfoRes beneficiaryInfoRes = beneficiariesInfoRes.getBeneficiariesMap()
				.get(item.getBeneficiaryId());
			item.setBeneficiaryPhoto(beneficiaryInfoRes.getPhoto());
			item.setBeneficiaryStatus(beneficiaryInfoRes.getStatus());
			item.setHospitalName(beneficiaryInfoRes.getHospital());
			item.setBeneficiaryName(beneficiaryInfoRes.getName());
			item.setBeneficiaryDisease(beneficiaryInfoRes.getDisease());
			item.setBeneficiaryBirthday(beneficiaryInfoRes.getBirthday().getSeconds() * 1000);
		});
		System.out.println("12323232323");
		return bookmarkResponses;
	}
}
