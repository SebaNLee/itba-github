package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.trainTickets.Destinations;
import ar.edu.itba.pod.grpc.trainTickets.Reservation;
import ar.edu.itba.pod.grpc.trainTickets.Ticket;
import ar.edu.itba.pod.grpc.trainTickets.TrainTicketServiceGrpc;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainServiceImpl extends TrainTicketServiceGrpc.TrainTicketServiceImplBase {

    private final TicketRepository ticketRepository;

    public TrainServiceImpl() {
        this.ticketRepository = new TicketRepository();
    }

    @Override
    public void getDestinations(Empty request, StreamObserver<Destinations> responseObserver) {
        List<String> destinations = new TicketRepository().getDestinations();
        responseObserver.onNext(Destinations.newBuilder().addAllDestinations(destinations).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getTrainsForDestination(StringValue request, StreamObserver<ar.edu.itba.pod.grpc.trainTickets.Train> responseObserver) {
        List<Train> trains = ticketRepository.getAvailability(request.getValue());
        trains.forEach(train->responseObserver.onNext(
                ar.edu.itba.pod.grpc.trainTickets.Train.newBuilder()
                        .setId(train.id())
                        .setDestination(train.destination())
                        .setTime(train.time())
                        .setAvailableCount(train.availableSeats())
                        .build()
        ));
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Ticket> purchaseTicket(StreamObserver<Reservation> responseObserver) {
        return new StreamObserver<>() {
            private final List<Ticket> tickets = new ArrayList<>();
            @Override public void onNext(Ticket ticket) {
                tickets.add(ticket);
            }
            @Override public void onCompleted() {
                String reservationId = ticketRepository.addReservation(tickets);
                Reservation reservation = Reservation.newBuilder()
                        .setId(reservationId)
                        .build();
                responseObserver.onNext(reservation);
                responseObserver.onCompleted();
            }
            @Override public void onError(Throwable throwable) {

            }
        };
    }

    @Override
    public StreamObserver<Reservation> getTicketsForReservations(StreamObserver<Ticket> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(Reservation reservation) {
                Optional<List<Ticket>> tickets =
                        ticketRepository.getReservation(reservation.getId());
                if (tickets.isPresent()) {
                    tickets.get().forEach(ticket -> responseObserver.onNext(ticket));
                } else { }
            }
            @Override public void onCompleted() { responseObserver.onCompleted(); }
            @Override public void onError(Throwable throwable) { }
        };
    }
}
