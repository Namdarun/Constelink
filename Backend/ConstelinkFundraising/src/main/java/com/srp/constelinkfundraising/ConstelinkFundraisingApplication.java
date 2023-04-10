package com.srp.constelinkfundraising;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ConstelinkFundraisingApplication {

	public static void main(String[] args) throws InterruptedException, IOException {

		ApplicationContext context = SpringApplication.run(ConstelinkFundraisingApplication.class, args);
		// HelloWorldServer helloWorldServer = context.getBean(HelloWorldServer.class);
		// ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9090);

		// 	int PORT = 9090;
		// 	Server server = ServerBuilder.forPort(PORT).addService(
		// 		new HelloWorldService()
		// 	).build();
		// 	server.start();
		// 	// server.awaitTermination();
	}

}
