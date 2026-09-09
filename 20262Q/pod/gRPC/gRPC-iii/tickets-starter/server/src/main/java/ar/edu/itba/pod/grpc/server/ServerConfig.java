package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.server.errorHandling.GlobalGrpcExceptionHandler;
import ar.edu.itba.pod.grpc.server.interceptor.GrpcServerResponseInterceptor;
import ar.edu.itba.pod.grpc.server.interceptor.ServerRequestLoggerInterceptor;
import io.grpc.ServerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;

@Configuration
public class ServerConfig {

//	@Bean
//	@Order(10)
//	@GlobalServerInterceptor
//	ServerInterceptor serverRequestLoggerInterceptor() {
//		return new ServerRequestLoggerInterceptor();
//	}
//
//	@Bean
//	@Order(20)
//	@GlobalServerInterceptor
//	GrpcServerResponseInterceptor serverResponseInterceptor() {
//		return new GrpcServerResponseInterceptor();
//	}
//
//	@Bean
//	GrpcExceptionHandler grpcExceptionHandler() {
//		return new GlobalGrpcExceptionHandler();
//	}

}
