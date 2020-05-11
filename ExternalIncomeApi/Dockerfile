FROM openjdk:8-jdk-alpine
ADD ./target/externalincomeapi.jar externalincomeapi.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "externalincomeapi.jar" ]