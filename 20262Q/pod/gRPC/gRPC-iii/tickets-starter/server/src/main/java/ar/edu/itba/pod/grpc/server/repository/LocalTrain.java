package ar.edu.itba.pod.grpc.server.repository;

public record LocalTrain(String id, String destination, String time, int availableSeats) {
}
