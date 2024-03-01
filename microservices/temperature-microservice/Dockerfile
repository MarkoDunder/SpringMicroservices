FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY build/libs/temperature-microservice-0.0.1-SNAPSHOT.jar app.jar
COPY readings.csv .
ENTRYPOINT ["java","-jar","/app.jar"]