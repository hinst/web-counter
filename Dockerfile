FROM eclipse-temurin:21.0.9_10-jdk-noble
RUN mkdir /app
RUN mkdir /app/data
WORKDIR /app
COPY target/web-counter-0.0.1-SNAPSHOT.jar app.jar
RUN chown -R 1000:1000 /app
USER 1000:1000
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx100m", "-jar", "app.jar"]