# Telco Footprint Service

A simple REST web service providing footprint service information for a fictional telco operator.

The service manages addresses and their related service availability (Internet, Telefon, Televizija)
and exposes footprint query endpoints to retrieve address data together with related services.

---

## Features

- Address CRUD
- Service CRUD (nested under Address)
- Footprint queries (Address with related services)
- JSON and XML request/response support
- HTTP Basic Authentication
- OpenAPI / Swagger documentation
- File-based H2 database
- Dockerized application

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security (Basic Auth)
- H2 Database (file-based)
- Springdoc OpenAPI
- Docker

---

## Authentication

All API endpoints are secured using **HTTP Basic Authentication**.

Credentials are provided via environment variables:

- `TELCO_BASIC_USER`
- `TELCO_BASIC_PASS`

No credentials are stored in source control.

---

## Running Locally (WSL / Linux)

### Prerequisites
- Java 21
- Docker (optional)
- Maven wrapper (included)

### (Optional) Run tests
```bash
./mvnw clean test
```
### Run the application
```bash 
export TELCO_BASIC_USER=telco
export TELCO_BASIC_PASS=telco123
./mvnw spring-boot:run
```

### The application will start on:
```
http://localhost:8080
Check:
http://localhost:8080/swagger-ui/index.html
```

## Running with Docker
### Prerequisites
- Docker Desktop (or Docker Engine)

### Build the Docker image
```
docker build -t telco:latest .
```

### Run the container
```
docker run --rm -p 8080:8080 \
  -e TELCO_BASIC_USER=telco \
  -e TELCO_BASIC_PASS=telco123 \
  telco:latest
```

## API Documentation (Swagger)
### Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```
Authentication is required to access the documentation.

## Database
- File-based H2 database
- Automatically seeded on first run
- Data persists across restarts when running locally

### H2 Console (development only)
```
http://localhost:8080/h2-console
```
### JDBC URL
```
jdbc:h2:file:./data/telco-db
```
### Username
```
sa
```
### Password
```
(empty)
```

## API Endpoints
### Address CRUD
```
GET    /api/addresses
GET    /api/addresses/{id}
POST   /api/addresses
PUT    /api/addresses/{id}
DELETE /api/addresses/{id}
```

### Service CRUD (nested under Address)
```
GET    /api/addresses/{addressId}/services
GET    /api/addresses/{addressId}/services/{serviceId}
POST   /api/addresses/{addressId}/services
PUT    /api/addresses/{addressId}/services/{serviceId}
DELETE /api/addresses/{addressId}/services/{serviceId}
```

## Footprint Queries
```
GET /api/footprint/addresses/{addressId}
GET /api/footprint/addresses/search?street=&city=&post=&streetNo=&postNo=
```
At least one query parameter must be provided for the search endpoint.

## Example Requests
```
curl -u telco:telco123 http://localhost:8080/api/addresses
curl -u telco:telco123 http://localhost:8080/api/footprint/addresses/1
```

## Notes
- Environment variables are used for authentication credentials
- No secrets are committed to the repository
- Docker image does not include database files or credentials