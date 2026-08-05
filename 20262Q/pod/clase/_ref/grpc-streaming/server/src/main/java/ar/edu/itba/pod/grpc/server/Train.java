package ar.edu.itba.pod.grpc.server;

public record Train(
        String id,
        String destination,
        String time,
        int availableSeats
) {
}
