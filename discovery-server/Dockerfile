# Dockerfile
# FROM openjdk:17-jdk-alpine

FROM openjdk:17-jdk-slim AS BUILD
# MAINTAINER Ali Ahmed
ARG JAR_FILE=target/discovery-server-0.0.1-SNAPSHOT.jar
COPY target/discovery-server-0.0.1-SNAPSHOT.jar discovery-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar discovery-server-0.0.1-SNAPSHOT.jar