FROM ubuntu:latest
LABEL authors="SGaertners"
FROM openjdk:17
copy ./target/secrets.jar secrets.jar
CMD ["java","-jar","secrets.jar"]

ENTRYPOINT ["top", "-b"]