FROM openjdk:21
COPY target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

