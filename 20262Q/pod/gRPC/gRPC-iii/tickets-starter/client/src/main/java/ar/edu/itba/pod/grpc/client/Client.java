package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.trainTickets.Reservation;
import ar.edu.itba.pod.grpc.trainTickets.Ticket;
import ar.edu.itba.pod.grpc.trainTickets.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Bean
    CommandLineRunner run(TicketServiceClient client) { // TODO
        return _ -> {
            // TODO

           SequencedCollection<String> destinations = client.getDestinations();
           log.info("Destinations:\n{}", destinations);

           SequencedCollection<Train> availability = client.getAvailability(destinations.getFirst());
           log.info("Availability:\n{}", availability);

//            Reservation reservation = client.addReservation(availability.getFirst().getId(),
//                    List.of("John", "Paul", "Ringo"));
//            log.info("Reservation:\n{}", reservation);

//            SequencedCollection<Ticket> tickets = client.getTicketsFor(List.of(reservation.getId()));
//            log.info("Tickets: {}", tickets);
        };
    }

}
