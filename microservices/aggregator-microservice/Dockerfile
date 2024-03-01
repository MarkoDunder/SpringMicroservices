FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY build/libs/aggregator-microservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]