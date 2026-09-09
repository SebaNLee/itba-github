FROM amazoncorretto:25-alpine-jdk

WORKDIR /app

RUN apk add --no-cache bash

COPY target/grpc-tickets-client-2026.1Q-bin.tar.gz .

RUN tar -xzf grpc-tickets-client-2026.1Q-bin.tar.gz \
    && rm grpc-tickets-client-2026.1Q-bin.tar.gz \
    && chmod +x grpc-tickets-client-2026.1Q/run-client.sh

WORKDIR /app/grpc-tickets-client-2026.1Q

ENTRYPOINT ["bash", "run-client.sh"]