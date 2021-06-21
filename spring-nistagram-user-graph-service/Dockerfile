FROM maven:3.8.1-openjdk-11 AS appBuild

LABEL maintainer="sasa.strbacki@protonmail.com"

WORKDIR /usr/src/spring_nistagram_user_graph_service
COPY . .
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:11 AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/spring_nistagram_user_graph_service/target/spring-nistagram-user-graph-service.jar ./
EXPOSE ${USER_GRAPH_SERVICE_PORT}
CMD java -jar spring-nistagram-user-graph-service.jar -Xms128M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp"

FROM azul/zulu-openjdk-alpine:11 AS appDebug
WORKDIR /app
COPY  target/spring-nistagram-user-graph-service.jar spring-nistagram-user-graph-service.jar
EXPOSE ${USER_GRAPH_SERVICE_PORT}
CMD java -jar spring-nistagram-user-graph-service.jar -Xms128M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp"