# Dockerfile
# FROM openjdk:17-jdk-alpine

FROM openjdk:17-jdk-slim AS BUILD
# MAINTAINER Ali Ahmed
ARG JAR_FILE=target/inventory-microservice-0.0.1-SNAPSHOT.jar
COPY target/inventory-microservice-0.0.1-SNAPSHOT.jar inventory-microservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar inventory-microservice-0.0.1-SNAPSHOT.jar