# DevOps Interview Dummy Service

## Overview

This micro-service is responsible for the following action within the dummy project:

- Responding to get requests
- Securing the access through username/password authentication (see Configuration section)
- Returning a list of access date to the /do-something-persistent endpoint


## Getting Started
There are three main ways of installing application:
- [Local](#local-installation)
- [Docker](#docker-installation)

### Local installation
Before running the application, please install its prerequisites:
- [Java 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Gradle 7.2](https://gradle.org)

To run from the master branch, follow the instructions below:
1. Clone web application repository locally.
    ```bash
    $ git clone https://github.com/rufusnufus/devops-interview-app.git
    $ cd devops-interview-app
    ```
2. Run the application. Web app will open at [http://localhost:8080/](http://localhost:8080/).
    ```bash
    $ SERVER_PORT=8080 SPRING_SECURITY_USER_NAME=admin SPRING_SECURITY_USER_PASSWORD=admin APPLICATION_FILE_PATH=/tmp/devops-dummy-app-access.txt gradle run
    ```

### Docker installation
Before running the application, please install its prerequisites:
* [Docker 20.10.7+](https://docs.docker.com/get-docker/)
* [Docker 20+, with BuildKit enabled](https://docs.docker.com/get-docker/);
To run from the master branch, follow the instructions below:
1. Clone web application repository locally.
    ```bash
    $ git clone https://github.com/rufusnufus/devops-interview-app.git
    $ cd devops-interview-app
    ```
2. [Optional] Build the image.
    ```bash
    $ docker build -t nufusrufus/devops-interview-app .
    ```
3. Run the container. Web app will open at [http://localhost:8000/](http://localhost:8000/).

    - Using `docker run` command:
      ```bash
      $ docker run -p 8080:8080 -p 8081:8081 -e SERVER_PORT=8080 -e APPLICATION_FILE_PATH=./data/devops-dummy-app-access.txt -e SPRING_SECURITY_USER_NAME=admin -e SPRING_SECURITY_USER_PASSWORD=admin -v data:/home/javauser/data nufusrufus/devops-interview-app
      ```
    - Using `docker-compose up` command:
      ```bash
      docker-compose up -d
      ```

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

After that run the application

```bash 
./gradlew run
```

## Configuration parameters

File with parameters - devops-interview-app/src/main/resources/application.yml

Environment variables for configuring:

- `SERVER_PORT` :int: port for the main http server
- `APPLICATION_FILE_PATH` :int: path of the file created by the application
- `SPRING_SECURITY_USER_NAME` :string: username for the authentication
- `SPRING_SECURITY_USER_PASSWORD` :string: password for the authentication

## Endpoints

By default, the application binds its http server on port `8080` and its monitoring port on `8081`. It exposes four
main endpoints:

- `GET /api/v2/do-something` on port `8080`: returns a string
- `GET /api/v2/do-something-persistent` on port `8080`: appends the access date on a file and returns the content of the
  file
- `GET /actuator/health/readiness` on port `8081`: readiness probe for the application
- `GET /actuator/health/liveness` on port `8081`: liven probe for the application

