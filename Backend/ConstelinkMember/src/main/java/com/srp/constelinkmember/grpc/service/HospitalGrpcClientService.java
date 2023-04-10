package com.srp.constelinkmember.grpc.service;

import org.springframework.stereotype.Service;

import com.srp.constelinkmember.HospitalGrpcServiceGrpc;
import com.srp.constelinkmember.HospitalInfoReq;
import com.srp.constelinkmember.HospitalInfoRes;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HospitalGrpcClientService {

	private final ManagedChannel channel = ManagedChannelBuilder.forAddress("constelink-beneficiary", 9090)
		.usePlaintext()
		.build();
	private HospitalGrpcServiceGrpc.HospitalGrpcServiceBlockingStub stub = HospitalGrpcServiceGrpc.newBlockingStub(
		channel);

	public HospitalInfoRes getHospitalInfo(Long id) throws StatusRuntimeException {
		log.info("request grpc....");
		final HospitalInfoRes response = this.stub.getHospitalRpc(HospitalInfoReq.newBuilder().setId(id).build());
		return response;

	}
}
