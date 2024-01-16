FROM amazoncorretto:17-alpine-jdk

WORKDIR /app
COPY target/test_em-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
