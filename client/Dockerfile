FROM eclipse-temurin:25-jre

WORKDIR /app

COPY target/client.jar client.jar
COPY docker-entrypoint.sh docker-entrypoint.sh

RUN chmod +x docker-entrypoint.sh

ENTRYPOINT ["./docker-entrypoint.sh"]