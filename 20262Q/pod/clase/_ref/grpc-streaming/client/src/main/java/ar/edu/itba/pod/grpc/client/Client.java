package ar.edu.itba.pod.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc.TrainTicketServiceStub;
import ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc;
import ar.edu.itba.pod.grpc.trainTickets.Reservation;
import ar.edu.itba.pod.grpc.trainTickets.Ticket;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        logger.info("grpc-streaming Client Starting ...");
        logger.info("grpc-com-patterns Client Starting ...");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        try {
            final TrainClient trainClient = new TrainClient(channel);
            trainClient.getDestinations().forEach(System.out::println);

            trainClient.getTrainsForDestination("Tucuman");

            trainClient.purchaseTicket();

            

        } finally {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }
    }
}
