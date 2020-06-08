FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8083
COPY ./target/time-0.0.1-SNAPSHOT.jar time.jar
ENTRYPOINT ["java", "-jar", "/time.jar"]