package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.trainTickets.Reservation;
import ar.edu.itba.pod.grpc.trainTickets.Ticket;
import ar.edu.itba.pod.grpc.trainTickets.Train;
import ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class TicketServiceClient {

    private static final Logger log = LoggerFactory.getLogger(TicketServiceClient.class);

    private final TrainTicketServiceGrpc.TrainTicketServiceBlockingStub blockingStub;
    private final TrainTicketServiceGrpc.TrainTicketServiceStub stub;

    public TicketServiceClient(TrainTicketServiceGrpc.TrainTicketServiceBlockingStub blockingStub,
                               TrainTicketServiceGrpc.TrainTicketServiceStub stub) {
        this.blockingStub = blockingStub;
        this.stub = stub;
    }

    public List<String> getDestinations() {
        // TODO
        return null;
    }

    public SequencedCollection<Train> getAvailability(String destination) {
        // TODO
        return null;
    }

    public Reservation addReservation(String train, SequencedCollection<String> names) {
        // TODO
        return null;
    }

    public SequencedCollection<Ticket> getTicketsFor(SequencedCollection<String> reservationIds) {
        // TODO
        return null;
    }

}
