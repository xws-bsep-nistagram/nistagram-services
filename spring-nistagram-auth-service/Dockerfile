FROM maven:3.8.1-openjdk-11 AS appBuild

LABEL maintainer="sasa.strbacki@protonmail.com"

WORKDIR /usr/src/spring_nistagram_auth_service
COPY . .
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:11 AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/spring_nistagram_auth_service/target/spring-nistagram-auth-service.jar ./
EXPOSE ${AUTH_SERVICE_PORT}
CMD java -jar spring-nistagram-auth-service.jar

FROM azul/zulu-openjdk-alpine:11 AS appDebug
WORKDIR /app
COPY  target/spring-nistagram-auth-service.jar spring-nistagram-auth-service.jar
EXPOSE ${AUTH_SERVICE_PORT}
CMD java -jar spring-nistagram-auth-service.jar