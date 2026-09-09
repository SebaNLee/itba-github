package ar.edu.itba.pod.grpc.server.repository;

import ar.edu.itba.pod.grpc.trainTickets.Ticket;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.IntStream;

@Repository
public class TicketRepository {

    private static final SequencedCollection<String> destinations = List.of("Mar del Plata", "Rosario", "Cordoba", "Tucuman");
    private static final SecureRandom random = new SecureRandom();
    private final Map<String, SequencedCollection<LocalTrain>> trainsByDestination = new HashMap<>();
    private final Map<String, SequencedCollection<Ticket>> reservations = new HashMap<>();

    public TicketRepository() {
        initSampleData();
    }

    private void initSampleData() {
        for (String destination : destinations) {
            trainsByDestination.putIfAbsent(destination, new ArrayList<>());
            IntStream.range(0, random.nextInt(1, 5))
                    .mapToObj(idx -> new LocalTrain(
                            UUID.randomUUID().toString(),
                            destination,
                            String.valueOf(idx + 1),
                            random.nextInt(10))
                    ).forEach(trainsByDestination.get(destination)::add);
        }
    }

    public SequencedCollection<String> getDestinations() {
        return destinations;
    }

    public SequencedCollection<LocalTrain> getAvailability(String destination) {
        return trainsByDestination.get(destination);
    }

    public String addReservation(SequencedCollection<Ticket> tickets) {
        String reservationId = UUID.randomUUID().toString();
        reservations.put(reservationId, tickets);
        return reservationId;
    }

    public Optional<SequencedCollection<Ticket>> getReservation(String id) {
        return Optional.ofNullable(reservations.get(id));
    }

}

