set JAVA_HOME=C:\Users\hinst\.jdks\temurin-21.0.5
mvn clean package -DskipTests
java -jar target\web-counter-0.0.1-SNAPSHOT.jar
