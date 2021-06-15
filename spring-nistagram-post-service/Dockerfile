FROM maven:3.8.1-openjdk-11 AS appBuild

LABEL maintainer="sasa.strbacki@protonmail.com"

WORKDIR /usr/src/spring_nistagram_post_service
COPY . .
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:11 AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/spring_nistagram_post_service/target/spring-nistagram-post-service.jar ./
EXPOSE ${POST_SERVICE_PORT}
CMD java -jar spring-nistagram-post-service.jar -Xms128M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp"

FROM azul/zulu-openjdk-alpine:11 AS appDebug
WORKDIR /app
COPY  target/spring-nistagram-post-service.jar spring-nistagram-post-service.jar
EXPOSE ${POST_SERVICE_PORT}
CMD java -jar spring-nistagram-post-service.jar -Xms128M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp"