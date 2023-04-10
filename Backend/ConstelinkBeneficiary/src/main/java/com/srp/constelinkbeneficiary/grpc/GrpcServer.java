package com.srp.constelinkbeneficiary.grpc;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Component

public class GrpcServer {
	public int PORT = 9090;
	private Server server;

	public GrpcServer(HospitalGrpcService hospitalGrpcService, BeneficiaryGrpcService beneficiaryGrpcService) throws
		IOException {
		this.server = ServerBuilder.forPort(this.PORT)
			.addService(beneficiaryGrpcService)
			.addService(hospitalGrpcService)
			.build().start();
		System.out.println("Grpc 서버시작 완료");

		// this.server.awaitTermination();

	}

}
