FROM openjdk:8-jre
COPY target/devboard-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
