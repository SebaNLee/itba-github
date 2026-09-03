package ar.edu.itba.pod.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.ImportGrpcClients;

import ar.edu.itba.pod.grpc.user.LoginInformation;
import ar.edu.itba.pod.grpc.user.UserServiceGrpc;

@ImportGrpcClients(target = "local", types = UserServiceGrpc.UserServiceBlockingStub.class)
@SpringBootApplication
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Bean
    CommandLineRunner run(UserServiceGrpc.UserServiceBlockingStub stub) {
        return _ -> {
            var request = LoginInformation.newBuilder()
                    .setUserName("foo")
                    .setPassword("foopass")
                    .build();
            IO.println(stub.doLogin(request));

            // TOOD uso de getRoles()...
        };
    }

}
