FROM maven:3.8.1-openjdk-11 AS appBuild

LABEL maintainer="sasa.strbacki@protonmail.com"

WORKDIR /usr/src/spring_nistagram_chat_service
COPY . .
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:11 AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/spring_nistagram_chat_service/target/spring-nistagram-chat-service.jar ./
EXPOSE 8080
CMD java -jar spring-nistagram-chat-service.jar -Xms128M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp"