FROM maven:3.8.1-openjdk-11 AS appBuild

LABEL maintainer="sasa.strbacki@protonmail.com"

WORKDIR /usr/src/spring_nistagram_media_service
COPY . .
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:11 AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/spring_nistagram_media_service/target/spring-nistagram-media-service.jar ./
EXPOSE ${MEDIA_SERVICE_PORT}
CMD java -jar spring-nistagram-media-service.jar

FROM azul/zulu-openjdk-alpine:11 AS appDebug
WORKDIR /app
COPY  target/spring-nistagram-media-service.jar spring-nistagram-media-service.jar
EXPOSE ${MEDIA_SERVICE_PORT}
CMD java -jar spring-nistagram-media-service.jar