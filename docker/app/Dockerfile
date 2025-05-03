FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /build
COPY . /build

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /build/target/*.jar /app/run.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/run.jar"]

#FROM mcr.microsoft.com/openjdk/jdk:21-distroless AS build
#
#WORKDIR /build
#COPY . /build
#
#RUN ./mvnw package
#
#FROM mcr.microsoft.com/openjdk/jdk:21-distroless
#
#WORKDIR /app
#COPY ./target/*.jar /app/run.jar
#
#EXPOSE 8080
#
#USER app
#ENTRYPOINT ["java", "-jar", "/app/run.jar"]