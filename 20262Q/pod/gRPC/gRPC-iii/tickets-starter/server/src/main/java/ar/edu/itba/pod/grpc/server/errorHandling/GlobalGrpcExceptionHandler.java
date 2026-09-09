package ar.edu.itba.pod.grpc.server.errorHandling;

import com.google.rpc.Code;
import io.grpc.*;
import io.grpc.protobuf.StatusProto;
import org.jspecify.annotations.Nullable;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;

import java.util.Map;

public class GlobalGrpcExceptionHandler implements GrpcExceptionHandler {

    private final Map<Class<? extends Throwable>, Code> errorCodesByException = Map.of(
            IllegalArgumentException.class, Code.INVALID_ARGUMENT
    );

    @Override
    public @Nullable StatusException handleException(Throwable exception) {
        var resolved = resolveExceptionCode(exception);
        if (resolved == null) {
            return null;
        }
        var mappedException = resolved.exception();
        var message = mappedException.getMessage();
        var rpcStatus = com.google.rpc.Status.newBuilder()
                .setCode(resolved.code().getNumber())
                .setMessage(message != null ? message : "Unknown message")
                .build();
        return StatusProto.toStatusException(rpcStatus);
    }

    private @Nullable ResolvedExceptionCode resolveExceptionCode(Throwable exception) {
        Throwable current = exception;
        while (current != null) {
            for (var entry : errorCodesByException.entrySet()) {
                if (entry.getKey().isAssignableFrom(current.getClass())) {
                    return new ResolvedExceptionCode(current, entry.getValue());
                }
            }
            current = current.getCause();
        }
        return null;
    }

    private record ResolvedExceptionCode(Throwable exception, Code code) {
    }

}
