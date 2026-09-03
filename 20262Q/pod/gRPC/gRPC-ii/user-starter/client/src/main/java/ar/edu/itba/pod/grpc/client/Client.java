package ar.edu.itba.pod.grpc.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.grpc.client.ImportGrpcClients;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import ar.edu.itba.pod.grpc.user.LoginInformation;
import ar.edu.itba.pod.grpc.user.User;
import ar.edu.itba.pod.grpc.user.UserRoles;
import ar.edu.itba.pod.grpc.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;

@ImportGrpcClients(target = "local", types = UserServiceGrpc.UserServiceBlockingStub.class)
@SpringBootApplication
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    // ej4
    @Bean
    UserServiceGrpc.UserServiceFutureStub userFutureStub(GrpcChannelFactory channels) {
        return UserServiceGrpc.newFutureStub(channels.createChannel("local"));
    }

    // ej5
    @Bean
    UserServiceGrpc.UserServiceStub userAsyncStub(GrpcChannelFactory channels) {
        // UserServiceStub es el AsyncStub
        // felicito al que diseño esto (es un tonto)
        return UserServiceGrpc.newStub(channels.createChannel("local"));
    }
    
    @Bean
    CommandLineRunner run(UserServiceGrpc.UserServiceBlockingStub stub, UserServiceGrpc.UserServiceFutureStub futureStub, UserServiceGrpc.UserServiceStub asyncStub) {
        return _ -> {
            var request = LoginInformation.newBuilder()
                    .setUserName("foo")
                    .setPassword("foopass")
                    .build();
            
            User user = stub.doLogin(request);
            IO.println(user);

            // ej4
            // getRoles() con FutureStub
            ListenableFuture<UserRoles> listenableFuture = futureStub.getRoles(user);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            
            try {
                    Futures.addCallback(listenableFuture, new FutureCallback<>() {
                    @Override
                    public void onSuccess(UserRoles userRoles) {
                        IO.println(userRoles);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        log.error(t.getMessage());
                        countDownLatch.countDown();
                    }
                }, executorService);                
            } catch (Exception e) {
                // TODO: handle exception
            }
            
            // ej5
            // AsyncStub
            asyncStub.getRoles(user, new StreamObserver<>() {
                @Override
                public void onNext(UserRoles value) {
                    IO.println(value);
                }

                @Override
                public void onError(Throwable t) {
                    // TODO
                }

                @Override
                public void onCompleted() {
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await(10, TimeUnit.SECONDS);
        };
    }
}
