# DualServe - Microservices Architecture with Spring Boot and Django ML

A modern microservices architecture demonstrating real-time anomaly detection using Spring Boot for API management and Django for ML inference, orchestrated with Docker Compose.

## 🏗️ Architecture Overview

This project implements a dual-service architecture:

- **Spring Boot API** (`spring-api`): Handles HTTP requests, WebSocket connections, and orchestrates ML predictions
- **Django ML Service** (`django-ml`): Provides ML inference using ONNX models for anomaly detection
- **Docker Compose**: Orchestrates both services with health checks and service discovery

## 🚀 Features

- **Real-time Anomaly Detection**: ML-powered scoring using Isolation Forest model
- **WebSocket Support**: Real-time alert broadcasting to connected clients
- **Health Monitoring**: Built-in health checks for both services
- **RESTful APIs**: Clean API design with proper error handling
- **Containerized Deployment**: Easy deployment with Docker and Docker Compose
- **Database Integration**: SQLite database for metrics and alerts storage

## 📋 Prerequisites

- Docker (version 20.10 or higher)
- Docker Compose (version 2.0 or higher)
- Git

## 🛠️ Quick Start

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd dualserve
   ```

2. **Build and run the services**
   ```bash
   docker compose up --build
   ```

3. **Access the services**
   - **Spring API**: http://localhost:8080
   - **Django ML Service**: http://localhost:8000
   - **API Documentation**: http://localhost:8080/swagger-ui.html

## 📡 API Endpoints

### Spring Boot API (Port 8080)

- `POST /metrics` - Submit metrics for anomaly detection
- `GET /actuator/health` - Health check endpoint
- WebSocket: `/topic/alerts` - Real-time alert notifications

### Django ML Service (Port 8000)

- `POST /score/` - ML inference endpoint
- `GET /score/health/` - Health check endpoint

## 🔧 Usage Examples

### Submit Metrics for Anomaly Detection

```bash
curl -X POST http://localhost:8080/metrics \
  -H "Content-Type: application/json" \
  -d '{
    "feature1": 0.5,
    "feature2": 0.3
  }'
```

### Connect to WebSocket for Real-time Alerts

```javascript
const socket = new WebSocket('ws://localhost:8080/ws');
socket.onmessage = function(event) {
    const alert = JSON.parse(event.data);
    console.log('New alert:', alert);
};
```

## 🧪 Running Tests

```bash
# Run Spring Boot tests
docker compose exec spring-api ./mvnw test

# Run Django tests
docker compose exec django-ml pytest
```

## 📁 Project Structure

```
dualserve/
├── docker-compose.yml          # Service orchestration
├── spring-api/                 # Spring Boot application
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/example/metrics/
│       ├── controller/         # REST controllers
│       ├── service/           # Business logic
│       ├── entity/            # Data models
│       └── repository/        # Data access layer
├── django-ml/                  # Django ML service
│   ├── Dockerfile
│   ├── requirements.txt
│   ├── manage.py
│   └── scoring/               # ML inference app
│       ├── views.py           # API endpoints
│       └── onnx/              # ML models
└── scoring/                    # Shared ML models
    └── onnx/
        └── isoforest.onnx     # Anomaly detection model
```

## 🔍 Technology Stack

### Spring Boot API
- **Framework**: Spring Boot 3.0.0
- **Language**: Java 17
- **Database**: SQLite with JPA/Hibernate
- **WebSocket**: Spring WebSocket
- **Documentation**: OpenAPI/Swagger
- **Build Tool**: Maven

### Django ML Service
- **Framework**: Django with Django REST Framework
- **Language**: Python
- **ML Runtime**: ONNX Runtime
- **ML Library**: Scikit-learn
- **Server**: Gunicorn
- **Testing**: pytest

## 🐳 Docker Configuration

Both services are containerized with:
- Health checks for service monitoring
- Service discovery via Docker Compose networking
- Environment variable configuration
- Optimized multi-stage builds

## 🔧 Development

### Local Development Setup

1. **Spring Boot Development**
   ```bash
   cd spring-api
   ./mvnw spring-boot:run
   ```

2. **Django Development**
   ```bash
   cd django-ml
   python manage.py runserver
   ```

### Adding New Features

1. **Spring Boot**: Add controllers in `controller/`, services in `service/`
2. **Django ML**: Add views in `scoring/views.py`, models in `scoring/models.py`
3. **ML Models**: Place ONNX models in `scoring/onnx/`

## 📊 Monitoring and Health Checks

Both services include health check endpoints:
- Spring Boot: `/actuator/health`
- Django ML: `/score/health/`

Docker Compose monitors these endpoints and can restart services if they become unhealthy.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Open an issue on GitHub
- Check the API documentation at http://localhost:8080/swagger-ui.html
- Review the health check endpoints for service status 