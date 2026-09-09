package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.server.repository.LocalTrain;
import ar.edu.itba.pod.grpc.server.repository.TicketRepository;
import ar.edu.itba.pod.grpc.trainTickets.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.SequencedCollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;

@Service
public class Servant extends TrainTicketServiceGrpc.TrainTicketServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(Servant.class);

    private final TicketRepository ticketRepository;

    public Servant(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override 
    public void getDestinations(Empty request, StreamObserver<Destinations> responseObserver) {
        var destinations = ticketRepository.getDestinations();
        Destinations response = Destinations.newBuilder().addAllDestinations(destinations).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();;
    }

    @Override 
    public void getTrainsForDestination(StringValue request, StreamObserver<Train> responseObserver) {
        var destination = request.getValue();
        SequencedCollection<LocalTrain> availability = ticketRepository.getAvailability(destination);
        availability.forEach(localTrain -> {
            Train train = Train.newBuilder()
                    .setId(localTrain.id())
                    .setDestination(localTrain.destination())
                    .setTime(localTrain.time())
                    .setAvailableSeats(localTrain.availableSeats())
                    .build();

            responseObserver.onNext(train);
        });

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Ticket> purchaseTicket(StreamObserver<Reservation> responseObserver) {
        return new StreamObserver<>() {
            private final SequencedCollection<Ticket> tickets = new ArrayList<>();
            @Override public void onNext(Ticket ticket) { tickets.add(ticket); }
            @Override public void onError(Throwable throwable) { }
            @Override public void onCompleted() {
                var reservationId = ticketRepository.addReservation(tickets);
                var reservation = Reservation.newBuilder()
                        .setId(reservationId).setTicketCount(tickets.size()).build();
                responseObserver.onNext(reservation);
                responseObserver.onCompleted();
            }
        };
    }
}