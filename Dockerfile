FROM openjdk:21
RUN mkdir /target
COPY app.jar /target/app.jar
WORKDIR /target
ENTRYPOINT ["java", "-jar", "app.jar"]