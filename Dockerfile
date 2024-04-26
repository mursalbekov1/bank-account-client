FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/bank-account-client-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]