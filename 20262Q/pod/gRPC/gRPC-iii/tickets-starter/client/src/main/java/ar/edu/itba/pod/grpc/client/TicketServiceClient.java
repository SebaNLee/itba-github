package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.trainTickets.Destinations;
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
        var request = Empty.newBuilder().build();
        Destinations response = blockingStub.getDestinations(request);
        List<String> destinations = response.getDestinationsList();

        return destinations;
    }

    public SequencedCollection<Train> getAvailability(String destination) {
        var request = StringValue.of(destination); // o patrón builder

        Iterator<Train> iterator = blockingStub.getTrainsForDestination(request);
        List<Train> toReturn = new ArrayList<>();
        iterator.forEachRemaining(toReturn::add);

        return toReturn;
    }

    public Reservation addReservation(String train, SequencedCollection<String> names) throws InterruptedException, ExecutionException {
        var responseFuture = new CompletableFuture<Reservation>();
        var responseObserver = new StreamObserver<Reservation>() {
            @Override public void onNext(Reservation r) { responseFuture.complete(r); }
            @Override public void onError(Throwable throwable) { }
            @Override public void onCompleted() { }
        };
        StreamObserver<Ticket> requestObserver = stub.purchaseTicket(responseObserver);
        List.of("John", "Paul", "Ringo").forEach(name -> {
            Ticket ticket = Ticket.newBuilder()
                    .setId(UUID.randomUUID().toString())
                    .setPassengerName(name).setTrainId(train).build();
            requestObserver.onNext(ticket);
        });
        requestObserver.onCompleted();
        Reservation reservation = responseFuture.get();

        return reservation;
    }

    public SequencedCollection<Ticket> getTicketsFor(SequencedCollection<String> reservationIds) {
        // TODO
        return null;
    }

}
