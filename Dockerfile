FROM openjdk:21
COPY target/5G-Sport-Backend-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]