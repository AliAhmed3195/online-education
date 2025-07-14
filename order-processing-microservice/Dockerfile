# Dockerfile
# FROM openjdk:17-jdk-alpine

FROM openjdk:17-jdk-slim AS BUILD
# MAINTAINER Ali Ahmed
ARG JAR_FILE=target/order-processing-microservice-0.0.1-SNAPSHOT.jar
COPY target/order-processing-microservice-0.0.1-SNAPSHOT.jar order-processing-microservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar order-processing-microservice-0.0.1-SNAPSHOT.jar