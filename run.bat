cls
set JAVA_HOME=C:\Users\hinst\.jdks\temurin-21.0.5
CALL mvn clean package -DskipTests
%JAVA_HOME%\bin\java -Dserver.port=8081 -jar target\web-counter-0.0.1-SNAPSHOT.jar
