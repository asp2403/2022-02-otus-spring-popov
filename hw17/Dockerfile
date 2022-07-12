FROM openjdk:11-jre-slim
COPY /target/hw17-0.0.1-SNAPSHOT.jar /opt/app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]