FROM eclipse-temurin:25-jre

WORKDIR /app

COPY target/server.jar server.jar
COPY Database.json Database.json

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "server.jar"]

