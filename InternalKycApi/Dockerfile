FROM openjdk:8-jdk-alpine
COPY  ./target/InternalKycApi-1.0-SNAPSHOT.jar /internalkycapi.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/internalkycapi.jar" ]