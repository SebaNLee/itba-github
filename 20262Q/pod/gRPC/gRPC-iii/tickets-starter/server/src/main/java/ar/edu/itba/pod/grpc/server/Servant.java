package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.server.repository.TicketRepository;
import ar.edu.itba.pod.grpc.trainTickets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Servant extends TrainTicketServiceGrpc.TrainTicketServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(Servant.class);

    private final TicketRepository ticketRepository;

    public Servant(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

}