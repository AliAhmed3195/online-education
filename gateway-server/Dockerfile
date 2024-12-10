# Dockerfile
# FROM openjdk:17-jdk-alpine

FROM openjdk:17-jdk-slim AS BUILD
# MAINTAINER Ali Ahmed
ARG JAR_FILE=target/gateway-server-0.0.1-SNAPSHOT.jar

COPY target/gateway-server-0.0.1-SNAPSHOT.jar gateway-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar gateway-server-0.0.1-SNAPSHOT.jar