package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.GreeterGrpc;
import ar.edu.itba.pod.grpc.HelloReply;
import ar.edu.itba.pod.grpc.HelloRequest;
import io.grpc.stub.StreamObserver;

import org.springframework.stereotype.Service;

@Service
public class Servant extends GreeterGrpc.GreeterImplBase {

	@Override
	public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
		HelloReply reply = HelloReply.newBuilder()
				.setMessage("Hello!! Este es mi server " + req.getName())
				.build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}
}
