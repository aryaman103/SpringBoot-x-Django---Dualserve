**DualServe**
DualServe is a microservices architecture demo that combines Spring Boot and Django to create a real-time anomaly detection system. The project showcases how modern containerized services can work together for ML inference and API management. Spring Boot handles HTTP requests, WebSocket connections, and orchestrates ML predictions, while Django provides ML inference using ONNX models for anomaly detection. The system uses an Isolation Forest model to detect anomalies in real-time data streams, with WebSocket support for live alert broadcasting. Built with Docker Compose for easy deployment and development, DualServe demonstrates how to integrate different technology stacks—Java Spring, Python Django, ONNX runtime, and container orchestration—into a unified, production-ready microservices environment.

**Project Structure**
dualserve/
- spring-api/     # Spring Boot 3.0, WebSocket, REST API, SQLite
- django-ml/      # Django + ONNX runtime, ML inference, scoring
- scoring/        # Shared ML models (ONNX files)
- docker-compose.yml

**Key Components**
Spring Boot API: REST endpoints, WebSocket for real-time alerts, SQLite database, health monitoring. Django ML Service: ONNX model inference, anomaly detection scoring, REST API endpoints. Isolation Forest: ML model for detecting anomalies in feature vectors. Docker Compose: Service orchestration with health checks and service discovery.

**Quick Start**
git clone <your-repo-url>
cd dualserve
docker compose up --build
Services: Spring API (8080), Django ML (8000), API Docs (8080/swagger-ui.html)

**API Endpoints**
POST /metrics - Submit data for anomaly detection
GET /actuator/health - Spring health check
POST /score/ - ML inference endpoint
WebSocket: /topic/alerts - Real-time notifications
Dev Environment
Java 17 (Spring Boot 3.0)
Python 3.11 (Django, ONNX Runtime)
Docker Compose (all-in-one stack)
SQLite database
