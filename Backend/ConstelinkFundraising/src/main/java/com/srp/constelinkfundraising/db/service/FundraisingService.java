package com.srp.constelinkfundraising.db.service;

import static com.srp.constelinkfundraising.ConstelinkFundraisingApplication.*;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.srp.beneficiaryrpc.BeneficiariesInfoReq;
import com.srp.beneficiaryrpc.BeneficiariesInfoRes;
import com.srp.beneficiaryrpc.BeneficiaryGrpcServiceGrpc;
import com.srp.beneficiaryrpc.BeneficiaryInfoReq;
import com.srp.beneficiaryrpc.BeneficiaryInfoRes;
import com.srp.constelinkfundraising.common.exception.CustomException;
import com.srp.constelinkfundraising.common.exception.CustomExceptionType;
import com.srp.constelinkfundraising.db.dto.enums.FundraisingByHopitalType;
import com.srp.constelinkfundraising.db.dto.enums.FundraisingSortType;
import com.srp.constelinkfundraising.db.dto.request.DonateRequest;
import com.srp.constelinkfundraising.db.dto.request.MakeFundraisingRequest;
import com.srp.constelinkfundraising.db.dto.response.FundraisingBeneficiaryResponse;
import com.srp.constelinkfundraising.db.dto.response.FundraisingResponse;
import com.srp.constelinkfundraising.db.dto.response.StatisticsResponse;
import com.srp.constelinkfundraising.db.dto.response.StatsDataResponse;
import com.srp.constelinkfundraising.db.entity.Category;
import com.srp.constelinkfundraising.db.entity.Fundraising;
import com.srp.constelinkfundraising.db.repository.BookmarkRepository;
import com.srp.constelinkfundraising.db.repository.CategoryRepository;
import com.srp.constelinkfundraising.db.repository.FundraisingRepository;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FundraisingService {



	private final FundraisingRepository fundraisingRepository;
	private final CategoryRepository categoryRepository;
	private final BookmarkRepository bookmarkRepository;
	private final RestTemplate restTemplate;
	private final ManagedChannel channel = ManagedChannelBuilder.forAddress(
			"constelink-beneficiary",9090)
		.usePlaintext()
		.build();
	public BeneficiaryGrpcServiceGrpc.BeneficiaryGrpcServiceBlockingStub stub = BeneficiaryGrpcServiceGrpc.newBlockingStub(
		channel);


	public Page<FundraisingResponse> getFundraisings(int page, int size,
		FundraisingSortType sortType, Long memberId) {
		Page<Fundraising> fundraising;
		HashSet<Long> memberBookmark =
			memberId < 1 ? new HashSet<>() : bookmarkRepository.findBookmarksByIdMemberId(memberId);
		switch (sortType) {
			case ALL:
				fundraising = fundraisingRepository.findAll(PageRequest.of(page, size));
				break;
			case FINISHED:
				fundraising = fundraisingRepository.findFundraisingsByFundraisingIsDone(true,
					PageRequest.of(page, size));
				break;
			case UNFINISHED:
				fundraising = fundraisingRepository.findFundraisingsByFundraisingIsDone(false,
					PageRequest.of(page, size));
				break;
			case START_DATE_ASC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingStartTime").ascending()));
				break;
			case START_DATE_DESC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingStartTime").descending()));
				break;
			case END_DATE_ASC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingEndTime").ascending()));
				break;
			case END_DATE_DESC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingEndTime").descending()));
				break;
			default:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingStartTime").descending()));
				break;
		}
		HashSet<Long> idList = new HashSet<>();
		Page<FundraisingResponse> fundraisingResponsePage = fundraising.map(
			fund -> {
				idList.add(fund.getBeneficiaryId());
				System.out.println(fund.getFundraisingWillUse());
				return new FundraisingResponse().builder()
					.fundraisingIsDone(fund.isFundraisingIsDone())
					.fundraisingPeople(fund.getFundraisingPeople())
					.fundraisingStory(fund.getFundraisingStory())
					.fundraisingThumbnail(fund.getFundraisingThumbnail())
					.fundraisingTitle(fund.getFundraisingTitle())
					.fundraisingAmountRaised(fund.getFundraisingAmountRaised())
					.fundraisingStartTime(
						fund.getFundraisingStartTime()
							.atZone(ZoneId.of("Asia/Seoul"))
							.toInstant()
							.toEpochMilli())
					.fundraisingId(fund.getId())
					.fundraisingEndTime(
						fund.getFundraisingEndTime().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
					.fundraisingAmountGoal(fund.getFundraisingAmountGoal())
					.beneficiaryId(fund.getBeneficiaryId())
					.categoryName(fund.getCategory().getCategoryName())
					.fundraisingBookmarked(memberBookmark.contains(fund.getId()))
					.fundraisingWillUse(fund.getFundraisingWillUse()==null?"":fund.getFundraisingWillUse())
					.build();
			}
		);

		//List 중복제거
		BeneficiariesInfoReq beneficiariesInfoReq = BeneficiariesInfoReq.newBuilder()
			.addAllId(idList.stream().toList()).build();
		BeneficiariesInfoRes beneficiariesInfoRes = stub.getBeneficiariesRpc(beneficiariesInfoReq);

		fundraisingResponsePage.getContent().stream().forEach(item -> {
			BeneficiaryInfoRes beneficiaryInfoRes = beneficiariesInfoRes.getBeneficiariesMap()
				.get(item.getBeneficiaryId());
			item.setBeneficiaryName(beneficiaryInfoRes.getName());
			item.setBeneficiaryDisease(beneficiaryInfoRes.getDisease());
			item.setBeneficiaryBirthday(beneficiaryInfoRes.getBirthday().getSeconds() * 1000);
			item.setBeneficiaryStatus(beneficiaryInfoRes.getStatus());
			item.setBeneficiaryPhoto(beneficiaryInfoRes.getPhoto());
			item.setHospitalName(beneficiaryInfoRes.getHospital());
		});
		return fundraisingResponsePage;
	}

	public Page<FundraisingBeneficiaryResponse> getFundraisingsBeneficiaries(int page, int size,
		FundraisingSortType sortType, Long memberId) {
		Page<Fundraising> fundraising;
		HashSet<Long> memberBookmark =
			memberId < 1 ? new HashSet<>() : bookmarkRepository.findBookmarksByIdMemberId(memberId);
		List<Category> categories = categoryRepository.findAll();
		Map<Long, String> categoriesMap = categories.stream()
			.collect(Collectors.toMap(Category::getId, Category::getCategoryName));
		switch (sortType) {
			case ALL:
				fundraising = fundraisingRepository.findAll(PageRequest.of(page, size));
				break;
			case FINISHED:
				fundraising = fundraisingRepository.findFundraisingsByFundraisingIsDone(true,
					PageRequest.of(page, size));
				break;
			case UNFINISHED:
				fundraising = fundraisingRepository.findFundraisingsByFundraisingIsDone(false,
					PageRequest.of(page, size));
				break;
			case START_DATE_ASC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingStartTime").ascending()));
				break;
			case START_DATE_DESC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingStartTime").descending()));
				break;
			case END_DATE_ASC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingEndTime").ascending()));
				break;
			case END_DATE_DESC:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingEndTime").descending()));
				break;
			default:
				fundraising = fundraisingRepository.findAll(
					PageRequest.of(page, size, Sort.by("fundraisingStartTime").descending()));
				break;
		}

		Page<FundraisingBeneficiaryResponse> getFundraisingResponsePage = fundraising.map(
			fund -> new FundraisingBeneficiaryResponse().builder()
				.fundraisingIsDone(fund.isFundraisingIsDone())
				.fundraisingPeople(fund.getFundraisingPeople())
				.fundraisingStory(fund.getFundraisingStory())
				.fundraisingThumbnail(fund.getFundraisingThumbnail())
				.fundraisingTitle(fund.getFundraisingTitle())
				.fundraisingAmountRaised(fund.getFundraisingAmountRaised())
				.fundraisingStartTime(
					fund.getFundraisingStartTime().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
				.fundraisingId(fund.getId())
				.fundraisingEndTime(
					fund.getFundraisingEndTime().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
				.fundraisingAmountGoal(fund.getFundraisingAmountGoal())
				.beneficiaryId(fund.getBeneficiaryId())
				.categoryName(fund.getCategory().getCategoryName())
				.fundraisingBookmarked(memberBookmark.contains(fund.getId()))
				.fundraisingWillUse(fund.getFundraisingWillUse()==null?"":fund.getFundraisingWillUse())
				.build()
		);

		return getFundraisingResponsePage;
	}

	@Transactional
	public Boolean donateFundraising(DonateRequest donateRequest) {
		// 돈 0원 이상 체크, 해당 기부 id 체크
		Fundraising fundraising = fundraisingRepository.findFundraisingById(donateRequest.getFundraisingId())
			.orElseThrow(() -> new CustomException(CustomExceptionType.FUNDRAISING_NOT_FOUND));
		if (donateRequest.getCash() <= 0) {
			throw new CustomException(CustomExceptionType.DONATION_MONEY_ERROR);
		}

		fundraising.setFundraisingAmountRaised(fundraising.getFundraisingAmountRaised() + donateRequest.getCash());

		return true;
	}


	@Transactional
	public Fundraising makeFundraising(MakeFundraisingRequest makeFundraisingRequest) {
		//fundraising 무결성 검사 필요(제대로 하려면 다른 서비스와 통신 필요)
		if(makeFundraisingRequest.getBeneficiaryId()<1) {
			throw new CustomException(CustomExceptionType.BENEFICIARY_NOT_FOUND);
		}
		if(makeFundraisingRequest.getFundraisingAmountGoal()<1) {
			throw new CustomException(CustomExceptionType.GOAL_AMOUNT_ERROR);
		}
		if(makeFundraisingRequest.getCategoryId()<1) {
			throw new CustomException(CustomExceptionType.CATEGORY_NOT_FOUND);
		}
		if(makeFundraisingRequest.getFundraisingTitle() == "") {
			throw new CustomException(CustomExceptionType.TITLE_NOT_FOUND);
		}

		BeneficiaryInfoReq beneficiariesInfoReq = BeneficiaryInfoReq.newBuilder()
			.setId(makeFundraisingRequest.getBeneficiaryId()).build();
		BeneficiaryInfoRes beneficiaryInfoRes;
		try {
			beneficiaryInfoRes = stub.getBeneficiaryRpc(beneficiariesInfoReq);
		} catch(Exception e) {
			throw new CustomException(CustomExceptionType.BENEFICIARY_NOT_FOUND);
		}
		Fundraising fundraising = Fundraising.builder()
			.fundraisingAmountGoal(makeFundraisingRequest.getFundraisingAmountGoal())
			.fundraisingThumbnail(makeFundraisingRequest.getFundraisingThumbnail())
			.fundraisingTitle(makeFundraisingRequest.getFundraisingTitle())
			.fundraisingStory(makeFundraisingRequest.getFundraisingStory())
			.fundraisingEndTime(new Timestamp(makeFundraisingRequest.getFundraisingEndTime()).toLocalDateTime()
				.atZone(ZoneId.of("Asia/Seoul"))
				.toLocalDateTime())
			.beneficiaryId(makeFundraisingRequest.getBeneficiaryId())
			.category(categoryRepository.findCategoryById(makeFundraisingRequest.getCategoryId()))
			.hospitalName(beneficiaryInfoRes.getHospital())
			.hospitalId(beneficiaryInfoRes.getHospitalId())
			.build();
		if(makeFundraisingRequest.getFundraisingWillUse()==null){
			fundraising.setFundraisingWillUse("");
		} else {
			fundraising.setFundraisingWillUse(makeFundraisingRequest.getFundraisingWillUse());
		}
		fundraisingRepository.saveAndFlush(fundraising);
		return fundraising;
	}

	public StatisticsResponse getFundraisingStatistics() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		StatsDataResponse response = restTemplate.getForObject("http://j8a206.p.ssafy.io:8997/donations/data",
			StatsDataResponse.class);
		Map<String,Long> fundraisingStatistics = fundraisingRepository.findFundraisingsStatistics();

		StatisticsResponse statisticsResponse = new StatisticsResponse().builder()
			.totalFundraisings(fundraisingStatistics.get("totalFundraisings"))
			.totalAmountedCash(fundraisingStatistics.get("totalAmountedCash"))
			.totalFundraisingsFinished(fundraisingStatistics.get("totalFundraisingsFinished"))
			.allDonation(response.getAllDonation())
			.allMember(response.getAllMember())
			.allHospital(response.getAllHospital())
			.build();

		return statisticsResponse;
	}

	public Page<FundraisingResponse> fundraisingByCategory(Long categoryId, Long memberId, int page, int size) {
		Page<Fundraising> fundraisingPage;
		if(categoryId<1) {
			fundraisingPage = fundraisingRepository.findAll(PageRequest.of(page,size));
		} else {
			fundraisingPage = fundraisingRepository.getFundraisingsByCategory_Id(categoryId, PageRequest.of(page,size));
		}


		HashSet<Long> memberBookmark =
			memberId < 1 ? new HashSet<>() : bookmarkRepository.findBookmarksByIdMemberId(memberId);
		HashSet<Long> idList = new HashSet<>();
		Page<FundraisingResponse> fundraisingResponsePage = fundraisingPage.map(
			fund -> {
				idList.add(fund.getBeneficiaryId());
				return new FundraisingResponse().builder()
					.fundraisingIsDone(fund.isFundraisingIsDone())
					.fundraisingPeople(fund.getFundraisingPeople())
					.fundraisingStory(fund.getFundraisingStory())
					.fundraisingThumbnail(fund.getFundraisingThumbnail())
					.fundraisingTitle(fund.getFundraisingTitle())
					.fundraisingAmountRaised(fund.getFundraisingAmountRaised())
					.fundraisingStartTime(
						fund.getFundraisingStartTime()
							.atZone(ZoneId.of("Asia/Seoul"))
							.toInstant()
							.toEpochMilli())
					.fundraisingId(fund.getId())
					.fundraisingEndTime(
						fund.getFundraisingEndTime().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
					.fundraisingAmountGoal(fund.getFundraisingAmountGoal())
					.beneficiaryId(fund.getBeneficiaryId())
					.categoryName(fund.getCategory().getCategoryName())
					.fundraisingBookmarked(memberBookmark.contains(fund.getId()))
					.hospitalName(fund.getHospitalName())
					.fundraisingWillUse(fund.getFundraisingWillUse()==null?"":fund.getFundraisingWillUse())
					.build();
			});
		BeneficiariesInfoReq beneficiariesInfoReq = BeneficiariesInfoReq.newBuilder()
			.addAllId(idList.stream().toList()).build();
		BeneficiariesInfoRes beneficiariesInfoRes = stub.getBeneficiariesRpc(beneficiariesInfoReq);


		fundraisingResponsePage.getContent().stream().forEach(item -> {
			BeneficiaryInfoRes beneficiaryInfoRes = beneficiariesInfoRes.getBeneficiariesMap()
				.get(item.getBeneficiaryId());
			item.setBeneficiaryName(beneficiaryInfoRes.getName());
			item.setBeneficiaryDisease(beneficiaryInfoRes.getDisease());
			item.setBeneficiaryBirthday(beneficiaryInfoRes.getBirthday().getSeconds() * 1000);
			item.setBeneficiaryStatus(beneficiaryInfoRes.getStatus());
			item.setBeneficiaryPhoto(beneficiaryInfoRes.getPhoto());
		});
		return fundraisingResponsePage;

	}

	public Page<FundraisingResponse> getFundraisingsByHospital(int page, int size,
		FundraisingByHopitalType sortType, Long hospitalId, Long memberId) {
		Page<Fundraising> fundraising;
		HashSet<Long> memberBookmark =
			memberId < 1 ? new HashSet<>() : bookmarkRepository.findBookmarksByIdMemberId(memberId);
		switch (sortType) {
			case FINISHED:
				fundraising = fundraisingRepository.getFundraisingsByHospitalIdAndFundraisingIsDone(hospitalId,true,
					PageRequest.of(page, size));
				break;
			case UNFINISHED:
				fundraising = fundraisingRepository.getFundraisingsByHospitalIdAndFundraisingIsDone(hospitalId,false,
					PageRequest.of(page, size));
				break;
			default:
				fundraising = fundraisingRepository.getFundraisingsByHospitalId(hospitalId,PageRequest.of(page, size));
				break;
		}
		HashSet<Long> idList = new HashSet<>();
		Page<FundraisingResponse> fundraisingResponsePage = fundraising.map(
			fund -> {
				idList.add(fund.getBeneficiaryId());
				return new FundraisingResponse().builder()
					.fundraisingIsDone(fund.isFundraisingIsDone())
					.fundraisingPeople(fund.getFundraisingPeople())
					.fundraisingStory(fund.getFundraisingStory())
					.fundraisingThumbnail(fund.getFundraisingThumbnail())
					.fundraisingTitle(fund.getFundraisingTitle())
					.fundraisingAmountRaised(fund.getFundraisingAmountRaised())
					.fundraisingStartTime(
						fund.getFundraisingStartTime()
							.atZone(ZoneId.of("Asia/Seoul"))
							.toInstant()
							.toEpochMilli())
					.fundraisingId(fund.getId())
					.fundraisingEndTime(
						fund.getFundraisingEndTime().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
					.fundraisingAmountGoal(fund.getFundraisingAmountGoal())
					.beneficiaryId(fund.getBeneficiaryId())
					.categoryName(fund.getCategory().getCategoryName())
					.fundraisingBookmarked(memberBookmark.contains(fund.getId()))
					.fundraisingWillUse(fund.getFundraisingWillUse()==null?"":fund.getFundraisingWillUse())
					.build();
			}
		);

		//List 중복제거
		BeneficiariesInfoReq beneficiariesInfoReq = BeneficiariesInfoReq.newBuilder()
			.addAllId(idList.stream().toList()).build();
		BeneficiariesInfoRes beneficiariesInfoRes = stub.getBeneficiariesRpc(beneficiariesInfoReq);

		fundraisingResponsePage.getContent().stream().forEach(item -> {
			BeneficiaryInfoRes beneficiaryInfoRes = beneficiariesInfoRes.getBeneficiariesMap()
				.get(item.getBeneficiaryId());
			item.setBeneficiaryName(beneficiaryInfoRes.getName());
			item.setBeneficiaryDisease(beneficiaryInfoRes.getDisease());
			item.setBeneficiaryBirthday(beneficiaryInfoRes.getBirthday().getSeconds() * 1000);
			item.setBeneficiaryStatus(beneficiaryInfoRes.getStatus());
			item.setBeneficiaryPhoto(beneficiaryInfoRes.getPhoto());
			item.setHospitalName(beneficiaryInfoRes.getHospital());
		});
		return fundraisingResponsePage;
	}


	public FundraisingResponse getFundraising(Long fundraisingId,Long memberId) {
		Map<String, Object> data = fundraisingRepository.getfund(fundraisingId,memberId)
			.orElseThrow(()-> new CustomException(CustomExceptionType.FUNDRAISING_NOT_FOUND));
		Fundraising fund = (Fundraising)data.get("fond");
		Boolean bookmarked = (Boolean)data.get("bookmarked");

		FundraisingResponse fundraisingResponse = new FundraisingResponse().builder()
			.fundraisingIsDone(fund.isFundraisingIsDone())
			.fundraisingPeople(fund.getFundraisingPeople())
			.fundraisingStory(fund.getFundraisingStory())
			.fundraisingThumbnail(fund.getFundraisingThumbnail())
			.fundraisingTitle(fund.getFundraisingTitle())
			.fundraisingAmountRaised(fund.getFundraisingAmountRaised())
			.fundraisingStartTime(
				fund.getFundraisingStartTime()
					.atZone(ZoneId.of("Asia/Seoul"))
					.toInstant()
					.toEpochMilli())
			.fundraisingId(fund.getId())
			.fundraisingEndTime(
				fund.getFundraisingEndTime().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
			.fundraisingAmountGoal(fund.getFundraisingAmountGoal())
			.beneficiaryId(fund.getBeneficiaryId())
			.categoryName(fund.getCategory().getCategoryName())
			.fundraisingBookmarked(bookmarked)
			.fundraisingWillUse(fund.getFundraisingWillUse()==null?"":fund.getFundraisingWillUse())
			.build();
		//List 중복제거
		BeneficiaryInfoReq beneficiaryInfoReq = BeneficiaryInfoReq.newBuilder().setId(fundraisingResponse.getBeneficiaryId()).build();
		BeneficiaryInfoRes beneficiaryInfoRes = stub.getBeneficiaryRpc(beneficiaryInfoReq);

		fundraisingResponse.setBeneficiaryName(beneficiaryInfoRes.getName());
		fundraisingResponse.setBeneficiaryDisease(beneficiaryInfoRes.getDisease());
		fundraisingResponse.setBeneficiaryStatus(beneficiaryInfoRes.getStatus());
		fundraisingResponse.setBeneficiaryPhoto(beneficiaryInfoRes.getPhoto());
		fundraisingResponse.setBeneficiaryBirthday(beneficiaryInfoRes.getBirthday().getSeconds() * 1000);
		fundraisingResponse.setHospitalName(beneficiaryInfoRes.getHospital());

		return fundraisingResponse;
	}
}
