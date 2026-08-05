package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc.newBlockingStub;

public class TrainClient {
    private final TrainTicketServiceGrpc.TrainTicketServiceBlockingStub blockingStub;
    private final TrainTicketServiceGrpc.TrainTicketServiceStub stub;

    public TrainClient(ManagedChannel channel) {
        this.blockingStub = newBlockingStub(channel);
        this.stub = TrainTicketServiceGrpc.newStub(channel);
    }

    public List<String> getDestinations() {
        return blockingStub.getDestinations(com.google.protobuf.Empty.newBuilder().build()).getDestinationsList();
    }

    public void getTrainsForDestination(String destination) {
        StringValue request = StringValue.of(destination);
        Iterator<ar.edu.itba.pod.grpc.trainTickets.Train> trainsForDestination = blockingStub.getTrainsForDestination(request);
        while (trainsForDestination.hasNext()) {
            System.out.println(trainsForDestination.next());
        }
    }

    public void purchaseTicket() throws ExecutionException, InterruptedException {
        CompletableFuture<ar.edu.itba.pod.grpc.trainTickets.Reservation> reservation = new CompletableFuture<>();
        StreamObserver<ar.edu.itba.pod.grpc.trainTickets.Reservation> purchaseResponse = new StreamObserver<>() {
            @Override public void onNext(ar.edu.itba.pod.grpc.trainTickets.Reservation r) {
                reservation.complete(r);
            }
            @Override public void onCompleted() { }
            @Override public void onError(Throwable throwable) { }
        };
        StreamObserver<ar.edu.itba.pod.grpc.trainTickets.Ticket> ticketStreamObserver = stub.purchaseTicket(purchaseResponse);
        Arrays.asList("John", "Paul", "Ringo").forEach(name -> {
            ticketStreamObserver.onNext(ar.edu.itba.pod.grpc.trainTickets.Ticket.newBuilder().setPassengerName(name).build());
        });
        ticketStreamObserver.onCompleted();
        ar.edu.itba.pod.grpc.trainTickets.Reservation toReturn = reservation.get();
        System.out.println(toReturn.getId());
        System.out.println(toReturn.getTicketCount());
    }
}
