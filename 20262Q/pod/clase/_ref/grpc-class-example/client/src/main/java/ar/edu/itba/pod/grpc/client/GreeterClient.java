package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.GreeterGrpc;
import ar.edu.itba.pod.grpc.HelloReply;
import ar.edu.itba.pod.grpc.HelloRequest;
import io.grpc.ManagedChannel;

public class GreeterClient {
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public GreeterClient(ManagedChannel channel) {
        this.blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public String callHello(final String name) {
        final HelloRequest helloRequest = HelloRequest.newBuilder().setName(name).build();
        final HelloReply reply = blockingStub.sayHello(helloRequest);
        return reply.getMessage();
    }
}
