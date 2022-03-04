# syntax=docker/dockerfile:1
FROM gradle:7.2.0-jdk11-alpine as build
ENV APP_HOME=/home/gradle/src
RUN mkdir $APP_HOME
WORKDIR $APP_HOME
COPY --chown=gradle:gradle build.gradle settings.gradle gradlew $APP_HOME
COPY --chown=gradle:gradle gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11:jre-11.0.14_9-alpine as prod
LABEL maintainer="r.talalaeva@innopolis.university"
RUN apk add dumb-init && \
    addgroup --system javauser && adduser -u 1000 -S -s /bin/false -h /home/javauser -G javauser javauser

WORKDIR /home/javauser
RUN mkdir data && chown -R javauser:javauser data
USER javauser

COPY --from=build --chown=javauser:javauser /home/gradle/src/build/libs/devops-interview-app-0.0.1-SNAPSHOT.jar ./app/app.jar

CMD "dumb-init" "java" "-jar" "app/app.jar"
