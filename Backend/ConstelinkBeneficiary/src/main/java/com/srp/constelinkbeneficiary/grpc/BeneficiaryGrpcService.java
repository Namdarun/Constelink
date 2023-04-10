package com.srp.constelinkbeneficiary.grpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.protobuf.Timestamp;
import com.srp.beneficiaryrpc.BeneficiariesInfoReq;
import com.srp.beneficiaryrpc.BeneficiariesInfoRes;
import com.srp.beneficiaryrpc.BeneficiaryGrpcServiceGrpc;
import com.srp.beneficiaryrpc.BeneficiaryInfoReq;
import com.srp.beneficiaryrpc.BeneficiaryInfoRes;
import com.srp.beneficiaryrpc.BeneficiaryInfoResOrBuilder;
import com.srp.constelinkbeneficiary.common.exception.CustomException;
import com.srp.constelinkbeneficiary.common.exception.CustomExceptionType;
import com.srp.constelinkbeneficiary.db.entity.Beneficiary;
import com.srp.constelinkbeneficiary.db.repository.BeneficiaryRepository;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BeneficiaryGrpcService extends BeneficiaryGrpcServiceGrpc.BeneficiaryGrpcServiceImplBase {
	private final BeneficiaryRepository beneficiaryRepository;

	@Override
	public void getBeneficiaryRpc(BeneficiaryInfoReq request, StreamObserver<BeneficiaryInfoRes> responseObserver) {

		Beneficiary beneficiary = beneficiaryRepository.findBeneficiaryById(request.getId()).orElseThrow(
			() -> new CustomException(CustomExceptionType.BENEFICIARY_NOT_FOUND));
		BeneficiaryInfoRes beneficiaryInfoRes = BeneficiaryInfoRes.newBuilder()
			.setName(beneficiary.getBeneficiaryName())
			.setDisease(beneficiary.getBeneficiaryDisease())
			.setHospital(beneficiary.getHospital().getHospitalName())
			.setPhoto(beneficiary.getBeneficiaryPhoto())
			.setStatus(beneficiary.getBeneficiaryStatus())
			.setBirthday(Timestamp.newBuilder().setSeconds(beneficiary.getBeneficiaryBirthday().getTime()).build())
			.setHospitalId(beneficiary.getHospital().getId())
			.build();
		responseObserver.onNext(beneficiaryInfoRes);
		responseObserver.onCompleted();
	}

	@Override
	public void getBeneficiariesRpc(BeneficiariesInfoReq request,
		StreamObserver<BeneficiariesInfoRes> responseObserver) {
		List<Beneficiary> beneficiaries = beneficiaryRepository.findAllById(request.getIdList());
		Map<Long, BeneficiaryInfoRes> beneficiaryInfoResMap = new HashMap<>();
		beneficiaries.stream().forEach((beneficiary) -> {
			beneficiaryInfoResMap.put(beneficiary.getId(), BeneficiaryInfoRes.newBuilder()
				.setBirthday(Timestamp.newBuilder().setSeconds(beneficiary.getBeneficiaryBirthday().getTime()))
				.setDisease(beneficiary.getBeneficiaryDisease())
				.setName(beneficiary.getBeneficiaryName())
				.setHospital(beneficiary.getHospital().getHospitalName())
				.setPhoto(beneficiary.getBeneficiaryPhoto())
				.setStatus(beneficiary.getBeneficiaryStatus())
				.build());
		});
		BeneficiariesInfoRes beneficiariesInfoRes = BeneficiariesInfoRes.newBuilder()
			.putAllBeneficiaries(beneficiaryInfoResMap)
			.build();
		responseObserver.onNext(beneficiariesInfoRes);
		responseObserver.onCompleted();
	}
}
