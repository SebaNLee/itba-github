package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.client.interceptor.ClientLoggerInterceptor;
import ar.edu.itba.pod.grpc.client.interceptor.ClientResponseLogger;
import ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.client.GlobalClientInterceptor;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class ClientConfig {

	private static final String TARGET = "local";

	@Bean
	ManagedChannel managedChannel(GrpcChannelFactory channels) {
		return channels.createChannel(TARGET);
	}

	@Bean
	TrainTicketServiceGrpc.TrainTicketServiceBlockingStub blockingStub(ManagedChannel managedChannel) {
		return TrainTicketServiceGrpc.newBlockingStub(managedChannel);
	}

	@Bean
	TrainTicketServiceGrpc.TrainTicketServiceStub asyncStub(ManagedChannel managedChannel) {
		return TrainTicketServiceGrpc.newStub(managedChannel);
	}

	@Bean
	TicketServiceClient ticketServiceClient(TrainTicketServiceGrpc.TrainTicketServiceBlockingStub stub, TrainTicketServiceGrpc.TrainTicketServiceStub asyncStub) {
		return new TicketServiceClient(stub, asyncStub);
	}

	// TODO

//	@Bean
//	@Order(10)
//	@GlobalClientInterceptor
//	ClientInterceptor clientLoggerInterceptor() {
//		return new ClientLoggerInterceptor();
//	}
//
//	@Bean
//	@Order(20)
//	@GlobalClientInterceptor
//	ClientInterceptor clientResponseLoggerInterceptor() {
//		return new ClientResponseLogger();
//	}

}
