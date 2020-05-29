FROM gradle:6.4.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src/Symphoniacus-API
COPY --chown=gradle:gradle ./Symphoniacus /home/gradle/src/Symphoniacus
WORKDIR /home/gradle/src/Symphoniacus-API

RUN gradle shadowJar --no-daemon

FROM openjdk:11-jre-slim

EXPOSE 9005

RUN mkdir /app

COPY --from=build /home/gradle/src/Symphoniacus-API/build/libs/Symphoniacus-API.jar /app/Symphoniacus-API.jar

ENTRYPOINT ["java","-jar","/app/Symphoniacus-API.jar"]
