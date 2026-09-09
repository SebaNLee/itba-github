package ar.edu.itba.pod.grpc.client.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ClientLoggerInterceptor implements ClientInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ClientLoggerInterceptor.class);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor,
                                                               CallOptions callOptions, Channel channel) {
        // Here goes your code #1
        String rpcId = UUID.randomUUID().toString();
        String serviceName = methodDescriptor.getServiceName();
        String methodName = methodDescriptor.getBareMethodName();
        log.info("Call {}: {}#{}", rpcId, serviceName, methodName);
        // Here goes your code #2
        return channel.newCall(methodDescriptor, callOptions);
    }

}