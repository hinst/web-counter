FROM eclipse-temurin:21.0.9_10-jdk-noble
RUN mkdir /app
WORKDIR /app
COPY target/web-counter-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]