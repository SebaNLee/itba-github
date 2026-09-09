package ar.edu.itba.pod.grpc;

import ar.edu.itba.pod.grpc.server.HealthConfig;
import ar.edu.itba.pod.grpc.server.Servant;
import ar.edu.itba.pod.grpc.server.repository.TicketRepository;
import ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.grpc.test.autoconfigure.AutoConfigureInProcessTransport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.grpc.client.ImportGrpcClients;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig(TicketTest.TestConfig.class)
@AutoConfigureInProcessTransport
public class TicketTest {
	@Autowired
	private TrainTicketServiceGrpc.TrainTicketServiceBlockingStub blockingStub;

	@Test
	void getDestinationsTest() {
		// TODO
	}

	@EnableAutoConfiguration
	@Import({ Servant.class, HealthConfig.class })
	@ImportGrpcClients(types = TrainTicketServiceGrpc.TrainTicketServiceBlockingStub.class)
	static class TestConfig {
		@Bean
		TicketRepository ticketRepository() {
			return new TicketRepository();
		}
	}

}