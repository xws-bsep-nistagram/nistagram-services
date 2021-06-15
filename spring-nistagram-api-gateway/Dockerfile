FROM maven:3.8.1-openjdk-11 AS appBuild

LABEL maintainer="sasa.strbacki@protonmail.com"

WORKDIR /usr/src/spring_nistagram_api_gateway
COPY . .
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:11 AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/spring_nistagram_api_gateway/target/spring-nistagram-api-gateway.jar ./
EXPOSE ${API_GATEWAY_PORT}
CMD java -jar spring-nistagram-api-gateway.jar -Xms128M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp"

FROM azul/zulu-openjdk-alpine:11 AS appDebug
WORKDIR /app
COPY  target/spring-nistagram-api-gateway.jar spring-nistagram-api-gateway.jar
EXPOSE ${API_GATEWAY_PORT}
CMD java -jar spring-nistagram-api-gateway.jar -Xms128M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp"


