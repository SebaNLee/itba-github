package ar.edu.itba.pod.grpc.server.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServerResponseInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(GrpcServerResponseInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        return next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
                    @Override
                    public void sendMessage(RespT message) {
                        log.info("Message being sent to client: {}", message);
                        super.sendMessage(message);
                    }
                }, metadata);
    }

}