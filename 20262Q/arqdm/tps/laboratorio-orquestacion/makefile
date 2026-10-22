.PHONY: up down build logs kafka-topics

up:
	docker compose up -d

down:
	docker compose down -v

build:
	docker compose build

logs:
	docker compose logs -f

kafka-topics:
	docker exec -it kafka kafka-topics --bootstrap-server localhost:29092 --list

test-kafka:
	docker exec -it kafka kafka-console-producer --bootstrap-server localhost:29092 --topic test-topic

# Build all services
build-all:
	go work sync
	cd checkout && go build -o checkout-service ./cmd
	cd payment && go build -o payment-service ./cmd
	cd inventory && go build -o inventory-service ./cmd
	cd sale && go build -o sale-service ./cmd

# Update dependencies
deps-update:
	go work sync
	cd schemas && go mod tidy
	cd checkout && go mod tidy
	cd payment && go mod tidy
	cd inventory && go mod tidy
	cd sale && go mod tidy


build-sale:
	cd sale && go build -o sale-service ./cmd
