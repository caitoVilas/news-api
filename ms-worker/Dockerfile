FROM openjdk:21

WORKDIR /app

COPY ./build/libs/ms-worker-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

CMD ["sh", "-c", "java", "-jar", "ms-worker-0.0.1-SNAPSHOT.jar"]