# DevOps Interview Dummy Service

## Overview

This micro-service is responsible for the following action within the dummy project:
- Responding to get requests 
- Securing the access through username/password authentication (see Configuration section)

## System requirements

This micro-service requires the following resources in kubernetes format:
```yaml
resources:
  requests:
    memory: "256Mi"
    cpu: "500m"
  limits:
    memory: "512Mi"
#    cpu: "" no limits used to allow for fast start-up
```

## Build, test & run

### Build
This project is written using Gradle and Java 11.

### Run unit tests
```bash
./gradlew test
```

### Run integration tests
```bash
./gradlew test
```

### Run

After that run container

```bash 
./gradlew run
```

## Configuration parameters


File with parameters - devops-interview-app/src/main/resources/application.yml

Environment variables for configuring:

- `SERVER_PORT` :int: port for the main http server
- `SPRING_SECURITY_USER_NAME` :string: username for the authentication
- `SPRING_SECURITY_USER_PASSWORD` :string: password for the authentication

## Endpoints
By default, the application binds its http server on port `8080` and its monitoring port on `8081`.
It exposes three main endpoints:
- `GET /api/v1/do-something` on port `8080`: business logic endpoint
- `GET /actuator/health/readiness` on port `8081`: readiness probe for the application
- `GET /actuator/health/liveness` on port `8081`: liven probe for the application