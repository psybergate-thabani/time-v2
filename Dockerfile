FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8081
COPY ./target/time.jar time.jar
ENTRYPOINT ["java", "-jar", "/time.jar"]