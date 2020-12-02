FROM adoptopenjdk:14-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/rso-image-upload-api-1.0-SNAPSHOT.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "rso-image-upload-api-1.0-SNAPSHOT.jar"]
