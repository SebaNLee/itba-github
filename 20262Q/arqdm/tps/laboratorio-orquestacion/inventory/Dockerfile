# Build stage
FROM golang:1.24-alpine AS builder

# No additional packages needed for public modules

WORKDIR /app

# Copy go.mod first for better caching (note: inventory doesn't have go.sum)
COPY inventory/go.mod ./inventory/
COPY schemas/ ./schemas/

# Download dependencies
WORKDIR /app/inventory
RUN go mod download

# Copy source code
WORKDIR /app
COPY inventory/ ./inventory/

# Build static binary with optimizations
WORKDIR /app/inventory
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build \
    -ldflags='-w -s -extldflags "-static"' \
    -a -installsuffix cgo \
    -o main ./cmd

# Final stage - use distroless for minimal attack surface
FROM gcr.io/distroless/static-debian12:nonroot

# Distroless already includes ca-certificates

# Copy the static binary
COPY --from=builder /app/inventory/main /app/main

# Use non-root user
USER nonroot:nonroot

# Expose port
EXPOSE 8082

# Run the application
ENTRYPOINT ["/app/main"]