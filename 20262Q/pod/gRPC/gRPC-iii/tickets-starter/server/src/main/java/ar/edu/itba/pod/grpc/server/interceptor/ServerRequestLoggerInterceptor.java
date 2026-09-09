package ar.edu.itba.pod.grpc.server.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ServerRequestLoggerInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ServerRequestLoggerInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        // Here goes your code #1
        String rpcId = UUID.randomUUID().toString();
        MethodDescriptor<ReqT, RespT> methodDescriptor = call.getMethodDescriptor();
        String serviceName = methodDescriptor.getServiceName();
        String methodName = methodDescriptor.getBareMethodName();
        log.info("Call {}: {}#{}", rpcId, serviceName, methodName);
        // Here goes your code #2
        return next.startCall(call, headers);
    }

}