# Dockerfile
# FROM openjdk:17-jdk-alpine

FROM openjdk:17-jdk-slim AS BUILD
# MAINTAINER Ali Ahmed
ARG JAR_FILE=target/auth-server-0.0.1-SNAPSHOT.jar
COPY target/auth-server-0.0.1-SNAPSHOT.jar auth-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT java -jar auth-server-0.0.1-SNAPSHOT.jar