# Dockerfile
# FROM openjdk:17-jdk-alpine

FROM openjdk:17-jdk-slim AS BUILD
# MAINTAINER Ali Ahmed
ARG JAR_FILE=target/config-server-0.0.1-SNAPSHOT.jar
COPY target/config-server-0.0.1-SNAPSHOT.jar config-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar config-server-0.0.1-SNAPSHOT.jar