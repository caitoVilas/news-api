FROM openjdk:21

WORKDIR /app

COPY ./build/libs/ms-news-0.0.1-SNAPSHOT.jar .

EXPOSE 8000

CMD ["sh", "-c", "java", "-jar", "ms-news-0.0.1-SNAPSHOT.jar"]
