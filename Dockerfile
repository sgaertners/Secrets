FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} secrets.jar
ENTRYPOINT ["java","-jar","/secrets.jar"]