package ar.edu.itba.pod.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;

import ar.edu.itba.pod.grpc.GreeterGrpc;
import ar.edu.itba.pod.grpc.HelloReply;
import ar.edu.itba.pod.grpc.HelloRequest;

@SpringBootApplication
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    // recibo channel y creo newBlockingStub()
    // channel apunta a "cloud", definido en /resources/application.yml
    @Bean
    GreeterGrpc.GreeterBlockingStub greeterStub(GrpcChannelFactory channels) {
        return GreeterGrpc.newBlockingStub(channels.createChannel("local")); // ej4
        // return GreeterGrpc.newBlockingStub(channels.createChannel("cloud")); // ej3
    }

    // y acá se define el método del .proto
    @Bean
    CommandLineRunner run(GreeterGrpc.GreeterBlockingStub greeter) {
        return _ -> {
            var name = "EHLO!!";
            HelloRequest request = HelloRequest.newBuilder().setName(name).build();
            HelloReply response = greeter.sayHello(request);
            log.info("\n\nSÍÍÍÍÍÍÍÍÍÍÍÍÍ LO LOGReEeEeEeE:\n me devolvió: {}\n", response.getMessage());
        };
    }

}
