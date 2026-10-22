# Build stage
FROM golang:1.24-alpine AS builder

# No additional packages needed for public modules

WORKDIR /app

# Copy go.mod and go.sum first for better caching
COPY payment/go.mod payment/go.sum ./payment/
COPY schemas/ ./schemas/

# Download dependencies
WORKDIR /app/payment
RUN go mod download

# Copy source code
WORKDIR /app
COPY payment/ ./payment/

# Build static binary with optimizations
WORKDIR /app/payment
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build \
    -ldflags='-w -s -extldflags "-static"' \
    -a -installsuffix cgo \
    -o main ./cmd

# Final stage - use distroless for minimal attack surface
FROM gcr.io/distroless/static-debian12:nonroot

# Distroless already includes ca-certificates

# Copy the static binary
COPY --from=builder /app/payment/main /app/main

# Use non-root user
USER nonroot:nonroot

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["/app/main"]
