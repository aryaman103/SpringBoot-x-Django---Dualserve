# DualServe

This project demonstrates a dual-service architecture with a Spring Boot API and a Django ML service, orchestrated with Docker Compose.

## Prerequisites

- Docker
- Docker Compose

## Usage

To build and run the services, execute the following command from the root directory:

```bash
docker compose up --build
```

The services will be available at:
- **Spring API**: `http://localhost:8080`
- **Django ML Service**: `http://localhost:8000`

To run the tests inside the containers:
```bash
docker compose exec spring-api ./mvnw test
docker compose exec django-ml pytest
``` 