package ar.edu.itba.pod.grpc.client.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientResponseLogger implements ClientInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ClientResponseLogger.class);

    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor,
                                                               CallOptions callOptions, Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(
                channel.newCall(methodDescriptor, callOptions)) {
            @Override public void start(Listener<RespT> respListener, Metadata headers) {
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<>(respListener) {
                    @Override public void onMessage(RespT message) {
                        log.info("Received response from Server: {}", message);
                        super.onMessage(message);
                    }
                }, headers);
            }
        };
    }

}