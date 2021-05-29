FROM maven:3.8.1-openjdk-11 AS appBuild

LABEL maintainer="sasa.strbacki@protonmail.com"

WORKDIR /usr/src/spring_nistagram_notification_service
COPY . .
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:11 AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/spring_nistagram_notification_service/target/spring-nistagram-notification-service.jar ./
EXPOSE ${NOTIFICATION_SERVICE_PORT}
CMD java -jar spring-nistagram-notification-service.jar

FROM azul/zulu-openjdk-alpine:11 AS appDebug
WORKDIR /app
COPY  target/spring-nistagram-notification-service.jar spring-nistagram-notification-service.jar
EXPOSE ${NOTIFICATION_SERVICE_PORT}
CMD java -jar spring-nistagram-notification-service.jar