# Dockerfile
# FROM openjdk:17-jdk-alpine

FROM openjdk:17-jdk-slim AS BUILD
# MAINTAINER Ali Ahmed
ARG JAR_FILE=target/user-microservice-0.0.1-SNAPSHOT.jar
COPY target/user-microservice-0.0.1-SNAPSHOT.jar user-microservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar user-microservice-0.0.1-SNAPSHOT.jar